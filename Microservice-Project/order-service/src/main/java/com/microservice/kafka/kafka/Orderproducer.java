package com.microservice.kafka.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.microservice.kafka.dto.OrderEvent;

@Service
public class Orderproducer {
	
	private Logger LOGGER = LoggerFactory.getLogger(Orderproducer.class);
	
	private NewTopic topic;
	
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;

	public Orderproducer(NewTopic topic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
		this.topic = topic;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void SendMessage(OrderEvent event) {
		
		LOGGER.info(String.format("Order sent -> %s", event.toString()));
		
		Message<OrderEvent> message = MessageBuilder
				.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topic.name())
				.build();
		
		kafkaTemplate.send(message);
		
	}
	
	

}
