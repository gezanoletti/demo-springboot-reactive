package com.gezanoletti.demospringbootreactive.setup;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class TestDbInitializer
{

    @Value("classpath:/sql/northwind.sql")
    private Resource resource;

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(final ConnectionFactory connectionFactory)
    {
        final var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(resource));
        return initializer;
    }
}
