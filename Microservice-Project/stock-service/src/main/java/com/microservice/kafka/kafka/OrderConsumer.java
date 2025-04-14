package com.microservice.kafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.microservice.kafka.dto.OrderEvent;

@Service
public class OrderConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class); //by using this LOGGER we are going to log the event that we consume from the kafka topic

	@KafkaListener(topics = "${spring.kafka.topic.name}" , groupId = "${spring.kafka.consumer.group-id}")
	public void consumer(OrderEvent event) {
		LOGGER.info(String.format("Order event received in stock service -> %s", event.toString()));
		
		//save the order data into the database
	}
	
	
	
}
