package com.ntapia.notifications.impl;

import com.ntapia.notifications.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static com.ntapia.notifications.impl.Util.ERROR_CREATE_SESSION;

public class ConsumerJmsSynchronousImpl implements Consumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerJmsSynchronousImpl.class);

    private static final String MESSAGES_NOT_FOUND = "Messages not found in: {}";
    private static final String ERROR_READ_MESSAGE = "Error reading a message";

    private final Session session;
    private final long timeout;

    public ConsumerJmsSynchronousImpl(Connection connection, long timeout) {
        try {
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.timeout = timeout;
        } catch (JMSException e) {
            LOGGER.error(ERROR_CREATE_SESSION, e);
            throw new ConectionJmsException(ERROR_CREATE_SESSION);
        }
    }


    @Override
    public String read(String queueName) {

        try {
            Destination destination = session.createQueue(queueName);
            MessageConsumer messageConsumer = session.createConsumer(destination);

            Message message = messageConsumer.receive(timeout);
            if (message != null) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                return text;
            }

            LOGGER.info(MESSAGES_NOT_FOUND, queueName);
            return null;

        } catch (JMSException e) {
            LOGGER.error("Error to read from " + queueName, e);
            throw new ConsumerException(ERROR_READ_MESSAGE);
        }
    }
}
