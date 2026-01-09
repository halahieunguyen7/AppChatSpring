package com.example.ChatApp.Infrastructure.Redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Configuration
public class RedisSubscriberConfig {

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory factory,
            MessageListenerAdapter wsEventListener
    ) {
        RedisMessageListenerContainer container =
                new RedisMessageListenerContainer();

        container.setConnectionFactory(factory);
        container.addMessageListener(
                wsEventListener,
                new PatternTopic("ws-events")
        );

        return container;
    }

    @Bean
    public MessageListenerAdapter wsEventListenerAdapter(
            WsEventSubscriber subscriber,
            ObjectMapper objectMapper
    ) {
        MessageListenerAdapter adapter =
                new MessageListenerAdapter(subscriber, "onMessage");

        adapter.setSerializer(
                new GenericJackson2JsonRedisSerializer(objectMapper)
        );

        return adapter;
    }
}
