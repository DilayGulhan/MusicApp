package com.dilay.youtubemusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication

public class ContentServiceAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceAPIApplication.class, args);
    }

}
