package com.learning;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@EnableRabbit
@SpringBootApplication
public class SpringRabbitMqListenerApp implements RabbitListenerConfigurer {

    @Value("${producer.exchange.name}")
    private String producerExchange;

    @Value("${producer.queue.name}")
    private String producerQueue;

    @Value("${producer.routing.key}")
    private String producerRoutingKey;

    public static void main(String[] args) {
        SpringApplication.run(SpringRabbitMqListenerApp.class, args);
    }


    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
        rabbitListenerEndpointRegistrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

    @Bean
    public TopicExchange getProducerExchange() {
        return new TopicExchange(producerExchange);
    }

    @Bean
    public Queue getProducerQueue() {
        return new Queue(producerQueue);
    }

    @Bean
    public Binding declareProducerBinding() {
        return BindingBuilder.bind(getProducerQueue()).to(getProducerExchange()).with(producerRoutingKey);
    }
    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }
}
