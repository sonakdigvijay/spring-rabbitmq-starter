package com.learning.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SpringRabbitMqConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SpringRabbitMqConsumer.class);

    private static final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "${producer.queue.name}")
    public void receiveMessageFromProducer(final Employee employee) {

        try {
            logger.info("Message received {} from producer", mapper.writeValueAsString(employee));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }
}
