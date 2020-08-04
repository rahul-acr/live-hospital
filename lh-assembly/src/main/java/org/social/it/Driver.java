package org.social.it;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "org.social.it")
public class Driver {
    public static void main(String[] args) {
        SpringApplication.run(Driver.class, args);
    }
}
