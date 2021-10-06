package com.mokpo.spring.batch.job;

import com.mokpo.spring.batch.domain.UserInfo;
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
import org.springframework.batch.item.ItemWriter;
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

        /**
         * Job 안에는 여러 Step이 존재하고 이 Step 안에는 Tasklet 혹은 Reader&Processor&Writer 묶음이 존재한다.
         * 때문에 Job의 배치 시나리오는 일반적으로
         * 1. Read : 데이터 저장소에서 특정 레코드 데이터를 읽는다.
         * 2. Processing : 데이터를 가공/처리한다.
         * 3. Writer : 수정된 데이터를 저장소에 다시 저장한다.
         * 이 단계를 거친다고 볼 수 있다.
         */

        @Autowired
        UserRepository userRepository;

        @Bean
        public Job inactiveUserJob(JobBuilderFactory jobBuilderFactory, Step inactiveJobStep){
            return jobBuilderFactory.get("inactiveUserJob") // Job 생성을 도와주는 빌더, inactiveUserJob이라는 JobBuilder를 생성한다.
                    //.preventRestart()
                    .start(inactiveJobStep) // inactiveJobStep 제일 먼저 실행
                    .build();
        }

        @Bean
        public Step inactiveJobStep(StepBuilderFactory stepBuilderFactory) {
            return stepBuilderFactory.get("inactiveUserStep") // StepBuilder 생성
                    .<UserInfo, UserInfo> chunk(5)
                    .reader(inactiveUserReader())
                    .processor(inactiveUserProcessor())
                    .writer(inactiveUserWriter())
                    .build();
        }
        @Bean
        @StepScope // 각 Step 마다 새로운 Bean 만들기 때문에 지연생성이 가능하다.
        public QueueItemReader<UserInfo> inactiveUserReader() {
            List<UserInfo> oldUsers = userRepository.findByUpdatedDateBeforeAndStatusEquals( // 휴면 회원 리스트를 가져온다.
                    LocalDateTime.now().minusYears(1), // 오늘로부터 1년 전 가입
                    UserStatus.ACTIVE); // 이면서 user status가 active인 레코드
            return new QueueItemReader<>(oldUsers); // 들어온 레코드들을 효율적으로 담아 쓰기 위해 Queue에 저장한다.
        }

        public ItemProcessor<UserInfo, UserInfo> inactiveUserProcessor() {
            return user -> user.setInactive(); // 담아온 데이터를 휴면회원으로 전환시킨다.
        }

        public ItemWriter<UserInfo> inactiveUserWriter() { // 앞에서 설정한 청크 단위를 받아 List 행..
            return ((List<? extends UserInfo> users) -> userRepository.saveAll(users)); // 수정된 데이터들을 다시 저장한다.
        }



}
