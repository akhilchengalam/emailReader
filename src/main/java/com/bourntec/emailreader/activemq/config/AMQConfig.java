package com.bourntec.emailreader.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import java.util.Arrays;

import javax.jms.Queue;

/**
 * @author Gopal
 *
 */
@Configuration
public class AMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    public Queue saveQueue() {
        return new ActiveMQQueue("emailreader.saveQueue");
    }
    
    @Bean
    public Queue persistQueue() {
        return new ActiveMQQueue("emailreader.persistQueue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setTrustedPackages(Arrays.asList("com.bourntec.emailreader"));
        factory.setBrokerURL(brokerUrl);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(activeMQConnectionFactory());
    }
}
