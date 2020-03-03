package com.learning.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SpringRabbitMqMessageProducer {

    private final Logger logger = LoggerFactory.getLogger(SpringRabbitMqMessageProducer.class);

    public void sendMessage(final RabbitTemplate rabbitTemplate, final String exchange, final String routingKey, final Object data) {
        logger.info("Sending message to queue using routing key: {}. Message: {}", routingKey, data);
        rabbitTemplate.convertAndSend(exchange, routingKey, data);
        logger.info("Message sent to the queue");
    }
}
