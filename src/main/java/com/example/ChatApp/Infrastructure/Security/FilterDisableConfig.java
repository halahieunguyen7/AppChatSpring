package com.example.ChatApp.Infrastructure.Security;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Filter;

@Component
public class FilterDisableConfig implements ApplicationRunner {

    private final ApplicationContext context;

    public FilterDisableConfig(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(ApplicationArguments args) {
        Map<String, Filter> filters = context.getBeansOfType(Filter.class);
        filters.forEach((name, filter) ->
                System.out.println(name + " -> " + filter.getClass().getName())
        );
    }
}