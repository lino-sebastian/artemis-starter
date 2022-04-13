package com.post.meridiem.artemis.config.publisher;

import com.post.meridiem.artemis.service.PublishMessageToArtemis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ArtemisPublisherConfiguration {
    @Bean
    @ConditionalOnProperty(name = "env.artemis.enabled", havingValue = "true")
    public PublishMessageToArtemis publishMessageToQueue() {
        return new PublishMessageToArtemis();
    }
}
