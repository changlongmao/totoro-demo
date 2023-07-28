package org.totoro.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "org.totoro")
@SpringBootApplication
public class TotoroDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TotoroDemoApplication.class, args);
    }

}
