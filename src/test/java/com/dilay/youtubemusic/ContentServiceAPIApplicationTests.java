package com.dilay.youtubemusic;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
@SpringBootTest
@ExtendWith(SpringExtension.class)
class ContentServiceAPIApplicationTests {

    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:11.1")
            .withDatabaseName("db")
            .withUsername("sa")
            .withPassword("sa")
            .withReuse(true);

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();

        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }

    @Test
    void contextLoads() {
        // Test metodu
    }
}