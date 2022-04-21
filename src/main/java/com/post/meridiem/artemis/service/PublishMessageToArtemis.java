package com.post.meridiem.artemis.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.apache.camel.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.messaging.support.MessageBuilder;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Map;
import java.util.function.BiConsumer;

import static com.post.meridiem.artemis.constants.StarterHeaderConstants.QUEUE_NAME_TO_PUBLISH;
import static java.util.Optional.ofNullable;
import static java.util.regex.Pattern.compile;

@Slf4j
public class PublishMessageToArtemis {
    @Autowired
    JmsTemplate jmsTemplate;

    public void pushMessageToQueue(
            @Header(QUEUE_NAME_TO_PUBLISH) String queueName,
            @Body String messageBody
    ) {
        log.info("Publishing message to : " + queueName);
        jmsTemplate.convertAndSend(queueName, MessageBuilder.withPayload(messageBody).build());
    }

    public void pushMessageToQueueWithAttributes(
            @Header(QUEUE_NAME_TO_PUBLISH) String queueName,
            @Body String messageBody, @Headers Map<String, Object> headers
    ) {
        log.info("Publishing message with headers to : " + queueName);
        headers.remove(QUEUE_NAME_TO_PUBLISH);
        jmsTemplate.convertAndSend(queueName, messageBody, getMessagePostProcessorWithHeaders(headers));
    }

    private MessagePostProcessor getMessagePostProcessorWithHeaders(Map<String, Object> headers) {
        return postProcessor -> {
            headers.forEach(getProcessorWithKeyValue(postProcessor));
            return postProcessor;
        };
    }

    private BiConsumer<String, Object> getProcessorWithKeyValue(Message postProcessor) {
        return (headerKey, headerValue) -> {
            try {
                if (isValidHeaderKey(headerKey)) {
                    postProcessor.setStringProperty(getHeaderKey(headerKey), getHeaderValue(headerValue));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        };
    }

    private boolean isValidHeaderKey(String headerKey) {
        return !compile("[^a-zA-Z0-9_]").matcher(headerKey).find();
    }

    private String getHeaderValue(Object headerValue) {
        return ofNullable(headerValue).orElse("").toString();
    }

    private String getHeaderKey(String headerKey) {
        return ofNullable(headerKey).orElse("");
    }
}
