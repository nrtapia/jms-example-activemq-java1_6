package com.ntapia.notifications;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

public class PublisherJmsImpl implements Publisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherJmsImpl.class);

    private static final String ERROR_CREATE_SESSION = "Error to create JMS session";
    private static final String ERROR_SEND_MESSAGE = "Error sending a message";
    private static final String MESSAGE_SENT_SUCCESS = "Message sent successfully to: {}";
    private static final String SEND_MESSAGE = "Send JMS to: {} message: {}";
    private static final String ERROR_CLOSE_MESSAGE_PRODUCER = "Error to close message producer";

    private final Session session;

    public PublisherJmsImpl(Connection connection) {
        try {
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            LOGGER.error(ERROR_CREATE_SESSION, e);
            throw new PublisherException(ERROR_CREATE_SESSION);
        }
    }


    @Override
    public void send(String target, String message) {
        LOGGER.debug(SEND_MESSAGE, target, message);

        Destination destination;
        MessageProducer messageProducer = null;
        try {
            destination = session.createQueue(target);
            messageProducer = session.createProducer(destination);

            TextMessage textMessage = session.createTextMessage(message);
            messageProducer.send(textMessage);
            LOGGER.debug(MESSAGE_SENT_SUCCESS, target);

        } catch (JMSException e) {
            LOGGER.error("Error to send destination: " + target + " " + e.getMessage(), e);
            throw new PublisherException(ERROR_SEND_MESSAGE);

        } finally {
            if (messageProducer != null) {
                try {
                    messageProducer.close();
                } catch (JMSException e) {
                    LOGGER.error(ERROR_CLOSE_MESSAGE_PRODUCER);
                }
            }
        }


    }
}