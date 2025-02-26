package com.example.config;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Declarables fanoutBindings() {
        Queue requestGatewayQueue = new Queue("gateway.order.request.queue", false);
        Queue responseGatewayQueue = new Queue("gateway.order.response.queue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");
        return new Declarables(
                requestGatewayQueue, responseGatewayQueue,
                directExchange,
                bind(requestGatewayQueue).to(directExchange).with("gateway.order.request"),
                bind(responseGatewayQueue).to(directExchange).with("gateway.order.response")
        );
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverterConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
