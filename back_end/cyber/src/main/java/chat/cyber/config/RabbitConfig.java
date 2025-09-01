package chat.cyber.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitConfig {

    @Value("${RABBIT_QUEUE_NAME}")
    public String QUEUE_NAME;

    @Bean
    public Queue notificacoesQueue() {
        return new Queue(QUEUE_NAME, true);
    }
}
