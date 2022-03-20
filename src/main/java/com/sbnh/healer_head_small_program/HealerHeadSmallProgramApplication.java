package com.sbnh.healer_head_small_program;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.sbnh.healer_head_small_program")
@EnableScheduling
@EnableCaching
public class HealerHeadSmallProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealerHeadSmallProgramApplication.class, args);
    }

}
