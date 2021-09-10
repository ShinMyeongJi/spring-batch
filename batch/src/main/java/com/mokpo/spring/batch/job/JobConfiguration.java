package com.mokpo.spring.batch.job;

import com.mokpo.spring.batch.domain.User;
import com.mokpo.spring.batch.domain.UserStatus;
import com.mokpo.spring.batch.job.item.QueueItemReader;
import com.mokpo.spring.batch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

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


        @Autowired
        UserRepository userRepository;

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

        @Bean
        @StepScope // 각 Step 마다 새로운 Bean 만들기 때문에 지연생성이 가능하다.
        public QueueItemReader<User> inactiveUserReader() {
            List<User> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals(
                    LocalDateTime.now().minusYears(1),
                    UserStatus.ACTIVE);

            return new QueueItemReader<>(oldUsers);
        }

        public ItemProcessor<User, User> inactiveUserProcessor() {

        }


}
