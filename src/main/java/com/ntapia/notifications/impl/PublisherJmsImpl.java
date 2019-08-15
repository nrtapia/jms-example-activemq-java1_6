package com.ntapia.notifications.impl;


import com.ntapia.notifications.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static com.ntapia.notifications.impl.Util.ERROR_CREATE_SESSION;

public class PublisherJmsImpl implements Publisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PublisherJmsImpl.class);

    private static final String ERROR_SEND_MESSAGE = "Error sending a message";
    private static final String MESSAGE_SENT_SUCCESS = "Message sent successfully to: {}";
    private static final String SEND_MESSAGE = "Send JMS to: {} message: {}";
    private static final String ERROR_CLOSE_MESSAGE_PRODUCER = "Error to close message producer";

    private final Connection connection;

    public PublisherJmsImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void send(String queueName, String message) {
        LOGGER.debug(SEND_MESSAGE, queueName, message);

        final Session session;
        try {
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            LOGGER.error(ERROR_CREATE_SESSION, e);
            throw new ConectionJmsException(ERROR_CREATE_SESSION);
        }


        Destination destination;
        MessageProducer messageProducer = null;
        try {
            destination = session.createQueue(queueName);
            messageProducer = session.createProducer(destination);

            TextMessage textMessage = session.createTextMessage(message);
            messageProducer.send(textMessage);
            LOGGER.debug(MESSAGE_SENT_SUCCESS, queueName);

        } catch (JMSException e) {
            LOGGER.error("Error to send destination: " + queueName + " " + e.getMessage(), e);
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