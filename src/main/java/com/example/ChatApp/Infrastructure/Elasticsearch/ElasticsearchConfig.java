package com.example.ChatApp.Infrastructure.Elasticsearch;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(
        basePackages = "com.example.ChatApp.Infrastructure.Search"
)
@Configuration
public class ElasticsearchConfig {
}
