package com.mokpo.spring.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
* @package : com.mokpo.spring.batch
* @name : BatchApplication
* @date : 21. 9. 8. 오후 8:20
* @author : home
* @version : 1.0.0
**/

@EnableBatchProcessing
@SpringBootApplication
public class BatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchApplication.class, args);
    }

}
