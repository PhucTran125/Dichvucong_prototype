package com.viettel.docsearch;

import com.viettel.docsearch.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class DocumentSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocumentSearchApplication.class, args);
    }
}
