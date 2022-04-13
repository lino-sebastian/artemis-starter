package com.post.meridiem.artemis.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.util.ErrorHandler;

import java.util.Map;

import static java.util.Objects.nonNull;

@Slf4j
public class ConsumeMessageFromArtemis implements ErrorHandler {
    @Autowired
    ProducerTemplate producerTemplate;

    @Value("#{${env.artemis.listener.queue.target.map}}")
    Map<String, String> queueTargetNames;

    @Value("#{(${env.artemis.listener.queue.target.map}).keySet().stream().findFirst().get()}")
    private String messageConsumerDestinationValue;

    @JmsListener(destination = "#{(${env.artemis.listener.queue.target.map}).keySet().stream().findFirst().get()}")
    public void queueListener(String message, @Headers Map<String, Object> headers) {
        String serviceConsumingTarget = queueTargetNames.get(messageConsumerDestinationValue);
        if (nonNull(serviceConsumingTarget))
            producerTemplate.sendBodyAndHeaders(serviceConsumingTarget, message, headers);
    }

    @Override
    public void handleError(Throwable throwable) {
        log.warn("Error while listening" + throwable);
    }
}
