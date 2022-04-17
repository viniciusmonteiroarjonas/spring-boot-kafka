package br.com.springKafka.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class kafkaTopicConfiguration {

    @Value("${topic.name}")
    private String topic;

    @Bean
    public NewTopic criaTopico() {
        return new NewTopic(topic, 1, (short) 1);
    }
}
