package com.tokioschool.filmotokio.batch;

import com.tokioschool.filmotokio.repository.FilmRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

@Slf4j
@Component
public class FilmExporterListner implements JobExecutionListener {

    private final FilmRepository filmRepository;

    @Autowired
    public FilmExporterListner(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }


    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Starting job: {} Records to be migrated: {}",
                jobExecution.getJobInstance().getJobName(),
                filmRepository.countAllByMigrateFalse());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.FAILED) {
            log.error("Ending job: {} status: {} error: {}",
                    jobExecution.getJobInstance().getJobName(),
                    jobExecution.getStatus(),
                    jobExecution.getExitStatus().getExitDescription());
        } else {
            Instant startTime = jobExecution.getStartTime().toInstant();
            Instant endTime = jobExecution.getEndTime().toInstant();
            long seconds = Duration.between(startTime, endTime).getSeconds();
            filmRepository.updateMigratedFilm(LocalDateTime.now());
            log.info("Ending job: {} status: {} seconds:{} Records to be migrated: {}",
                    jobExecution.getJobInstance().getJobName(),
                    jobExecution.getStatus(),
                    seconds,
                    filmRepository.countAllByMigrateFalse());
        }
    }
}
