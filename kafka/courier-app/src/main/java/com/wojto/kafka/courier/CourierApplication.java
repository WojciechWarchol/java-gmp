package com.wojto.kafka.courier;

import com.wojto.kafka.model.Notification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class CourierApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourierApplication.class, args);
	}

	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public KafkaTemplate<String, Notification> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	/* Producer */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props =
				new HashMap<>(kafkaProperties.buildProducerProperties());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		props.put(ProducerConfig.ACKS_CONFIG, "1");
		return props;
	}

	@Bean
	public ProducerFactory<String, Notification> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}


	/* Consumer */
	@Bean
	public Map<String, Object> consumerConfigs() {
		Map<String, Object> props = new HashMap<>(
				kafkaProperties.buildConsumerProperties()
		);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				JsonDeserializer.class);
		props.put(ConsumerConfig.GROUP_ID_CONFIG,
				"tpd-loggers");

		return props;
	}

	@Bean
	public ConsumerFactory<String, Notification> consumerFactory() {
		final JsonDeserializer<Notification> jsonDeserializer = new JsonDeserializer<>(Notification.class);
		jsonDeserializer.addTrustedPackages("*");
		return new DefaultKafkaConsumerFactory<>(
				kafkaProperties.buildConsumerProperties(), new StringDeserializer(), jsonDeserializer
		);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, Notification> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Notification> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());

		return factory;
	}

}
