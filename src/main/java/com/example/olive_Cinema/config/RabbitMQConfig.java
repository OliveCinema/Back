package com.example.olive_Cinema.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "seatReservationQueue";
    public static final String DLQ_NAME = "deadLetterQueue";
    public static final String EXCHANGE_NAME = "seatReservationExchange";

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", DLQ_NAME)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ_NAME);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("seat.reserve.#");
    }

}
