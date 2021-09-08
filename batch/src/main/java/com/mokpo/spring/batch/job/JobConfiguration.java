package com.mokpo.spring.batch.job;

import com.mokpo.spring.batch.domain.User;
import com.mokpo.spring.batch.job.item.QueueItemReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @package : com.mokpo.spring.batch.job
* @name : JobConfiguration
* @date : 21. 9. 8. 오후 8:38
* @author : home
* @version : 1.0.0
**/

@Slf4j
@Configuration
public class JobConfiguration {

    @Bean
    public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep){
        return jobBuilderFactory.get("inactiveUserJob")
                    .preventRestart()
                    .start(inactiveJobStep)
                    .build();
    }

    @Bean
    public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("inactiveUserStep")
                .<User, User> chunk(10)
                .reader(inactiveUserReader())
                .processor(inactiveUserProcessor())
                .writer(inactiveUserWriter())
                .build();
    }

    public QueueItemReader inactiveUserReader() {
        return
    }
}
