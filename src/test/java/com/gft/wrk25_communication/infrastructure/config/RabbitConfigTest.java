package com.gft.wrk25_communication.infrastructure.config;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class RabbitConfigTest {

    @Autowired
    private com.gft.wrk25_communication.communication.infrastructure.config.RabbitConfig rabbitConfig;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    void testMessageConverter() {
        assertThat(rabbitConfig.messageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

    @Test
    void testRabbitTemplate() {
        RabbitTemplate template = rabbitConfig.rabbitTemplate(connectionFactory, rabbitConfig.messageConverter());
        assertThat(template).isNotNull();
        assertThat(template.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

}