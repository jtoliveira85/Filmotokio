package com.tokioschool.filmotokio.batch;

import com.tokioschool.filmotokio.domain.Film;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobRepository jobRepository; // Adicionando JobRepository

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory, JobRepository jobRepository) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobRepository = jobRepository;
    }

    @Bean
    public Step step(ItemReader<Film> reader, ItemProcessor<Film, Film> itemProcessor, ItemWriter<Film> writer) {
        return stepBuilderFactory.get("STEP")
                .<Film, Film>chunk(2)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public ItemProcessor<Film, Film> processor() {

        return film -> {
            Film migrateFilm = new Film();

            migrateFilm.setId(film.getId());
            migrateFilm.setTitle(film.getTitle());
            migrateFilm.setYear(film.getYear());
            migrateFilm.setDuration(film.getDuration());
            migrateFilm.setSypnosis(film.getSypnosis());


            return migrateFilm;
        };
    }

    @Bean
    public Job movieMigrationJob(Step step) {
        return jobBuilderFactory.get("FILM-MIGRATION")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .repository(jobRepository) // Definindo o JobRepository
                .build();
    }

    @Bean
    public JpaPagingItemReader<Film> reader(EntityManagerFactory entityManagerFactory) {
        return new JpaPagingItemReaderBuilder<Film>()
                .name("filmReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT f FROM Film f WHERE f.migrate = false")
                .build();
    }

    @Bean
    public FlatFileItemWriter<Film> writer() {
        FlatFileItemWriter<Film> writer = new FlatFileItemWriter<>();

        writer.setResource(new FileSystemResource(Paths.get("src/main/resources/static/exports", "film.csv")));

        writer.setAppendAllowed(true);

        writer.setHeaderCallback(writerHeader -> writerHeader.write("id,title,year,duration,sypnosis"));
        writer.setLineAggregator(new DelimitedLineAggregator<>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<>() {{
                setNames(new String[]{"id", "title", "year", "duration", "sypnosis"});
            }});
        }});

        return writer;
    }



}
