package com.example.ChatApp.Infrastructure.Consumer;

import com.example.ChatApp.Domain.Conversation.Event.MessageSentEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MessageSentEvent>
    kafkaListenerContainerFactory(
            ConsumerFactory<String, MessageSentEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, MessageSentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }
}
