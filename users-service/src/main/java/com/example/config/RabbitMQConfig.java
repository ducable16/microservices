package com.example.config;

import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.amqp.core.BindingBuilder.bind;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Declarables fanoutBindings() {
        Queue requestGatewayQueue = new Queue("gateway.user.request.queue", false);
        Queue responseGatewayQueue = new Queue("gateway.user.response.queue", false);
        Queue requestOrderQueue = new Queue("order.user.request.queue", false);
        Queue responseOrderQueue = new Queue("order.user.response.queue", false);
        DirectExchange directExchange = new DirectExchange("directExchange");
        return new Declarables(
                requestOrderQueue, responseOrderQueue, requestGatewayQueue, responseGatewayQueue,
                directExchange,
                bind(requestGatewayQueue).to(directExchange).with("gateway.user.request"),
                bind(requestOrderQueue).to(directExchange).with("order.user.request"),
                bind(responseOrderQueue).to(directExchange).with("order.user.response"),
                bind(responseGatewayQueue).to(directExchange).with("gateway.user.response")
        );
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverterConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
