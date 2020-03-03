package com.learning.controller;

import com.learning.model.Employee;
import com.learning.producer.SpringRabbitMqMessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rabbitmq")
public class SpringRabbitMqController {

    private static final Logger logger = LoggerFactory.getLogger(SpringRabbitMqController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SpringRabbitMqMessageProducer producer;

    @Value("${producer.exchange.name}")
    private String exchange;

    @Value("${producer.routing.key}")
    private String routingKey;

    @RequestMapping(value = "/marco")
    public String marco() {
        return "polo";
    }

    @RequestMapping(value = "/sendit")
    public ResponseEntity<?> sendit() {
        try {
            Employee employee = new Employee();
            employee.setEmployeeName("Digvijay Sonak");
            employee.setEmployeeAge(29);
            employee.setEmployeeDesignation("SSE");
            employee.setEmployeeId(8);
            producer.sendMessage(rabbitTemplate, exchange, routingKey, employee);
            return new ResponseEntity<String>("Message sent to queue", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception occurred while sending the message {}", e);
            return new ResponseEntity("Error while sending message to queue",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
