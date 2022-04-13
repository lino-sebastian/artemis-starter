package com.post.meridiem.artemis.config.listener;

import com.post.meridiem.artemis.service.ConsumeMessageFromArtemis;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "env.artemis.listener.queue.target.map")
public class ArtemisListenerConfiguration {

    @Bean
    @ConditionalOnProperty(name = "env.artemis.listener.queue.target.map")
    public ConsumeMessageFromArtemis consumeMessageFromArtemis() {
        return new ConsumeMessageFromArtemis();
    }

}
