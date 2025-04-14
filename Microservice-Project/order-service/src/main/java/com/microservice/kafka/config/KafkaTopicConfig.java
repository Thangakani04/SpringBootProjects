package com.microservice.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	@Value("${spring.kafka.topic.name}")
	private String topicName;
	
	//configure a topic using springbean
	//Spring Bean for Kafka Topic
	
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name(topicName)
			//	.partitions(3)  //if we want to create partitions we can create like this , basically we rely on default partition that is created by kafka				
				.build();
	}

}
