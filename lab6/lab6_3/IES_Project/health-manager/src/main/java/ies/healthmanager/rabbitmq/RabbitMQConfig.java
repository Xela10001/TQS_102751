
package ies.healthmanager.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ies.healthmanager.rabbitmq")
public class RabbitMQConfig {

    public static final String QUEUE_PATIENT_DATA = "patients-queue";
    public static final String EXCHANGE_PATIENT_DATA = "patients-exchange";
    public static final String QUEUE_DEAD_PATIENT_DATA = "dead-patients-queue";
    @Bean
    Queue patientsDataQueue() {
        return QueueBuilder.durable(QUEUE_PATIENT_DATA)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_PATIENT_DATA)
                .withArgument("x-message-ttl", 30000) // if message is not consumed in 30 seconds send to DLQ
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_PATIENT_DATA).build();
    }

    @Bean
    TopicExchange patientsDataExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_PATIENT_DATA).build();
    }

    @Bean
    Binding binding(Queue patientsDataQueue, TopicExchange patientsDataExchange) {
        return BindingBuilder.bind(patientsDataQueue).to(patientsDataExchange).with(QUEUE_PATIENT_DATA);
    }

}
