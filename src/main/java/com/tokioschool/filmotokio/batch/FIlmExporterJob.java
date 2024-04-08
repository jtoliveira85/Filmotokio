package com.tokioschool.filmotokio.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@EnableScheduling
public class FIlmExporterJob {

    private final JobLauncher jobLauncher;
    private final Job job;

    private final FilmExporterListner filmExporterListener;

    @Autowired
    public FIlmExporterJob(JobLauncher jobLauncher, Job job, FilmExporterListner filmExporterListener) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.filmExporterListener = filmExporterListener;
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void export() {


        JobParameters jobParameters = new JobParametersBuilder().addString("jobExecution", UUID.randomUUID().toString()).toJobParameters();

        try {
            JobExecution jobExecution = jobLauncher.run(job, jobParameters);
            filmExporterListener.beforeJob(jobExecution); // Chamar o método beforeJob do FilmExporterListner
            // Após o término do job, o método afterJob será chamado automaticamente pelo Spring Batch
            filmExporterListener.afterJob(jobExecution);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
