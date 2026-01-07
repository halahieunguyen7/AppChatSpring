package com.example.ChatApp.Infrastructure.Consumer;

import org.apache.kafka.connect.json.JsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConnectConverterConfig {

    @Bean
    public JsonConverter valueConverter() {
        JsonConverter converter = new JsonConverter();

        Map<String, Object> props = new HashMap<>();
        props.put("schemas.enable", false);

        converter.configure(props, false);
        return converter;
    }
}