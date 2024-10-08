package com.dogan.bilisim.notificationcontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.dogan.bilisim.dao")
@EntityScan("com.dogan.bilisim")
@ComponentScan("com.dogan.bilisim")
public class NotificationControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationControllerApplication.class, args);
    }

}
