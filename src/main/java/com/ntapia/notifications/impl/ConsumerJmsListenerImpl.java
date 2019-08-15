package com.ntapia.notifications.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class ConsumerJmsListenerImpl implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerJmsListenerImpl.class);

    private static final String ASYNC_MESSAGE = "Async Message: {}";

    @Override
    public void onMessage(Message message) {

        if (message != null) {
            try {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();

                LOGGER.info(ASYNC_MESSAGE, text);

            } catch (JMSException e) {
                LOGGER.error("Error listener message: " + e.getMessage(), e);
            }
        }
    }
}
