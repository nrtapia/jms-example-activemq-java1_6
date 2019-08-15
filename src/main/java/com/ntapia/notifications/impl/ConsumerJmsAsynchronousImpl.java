package com.ntapia.notifications.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

import static com.ntapia.notifications.impl.Util.ERROR_CREATE_SESSION;

public class ConsumerJmsAsynchronousImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerJmsAsynchronousImpl.class);

    private static final String ERROR_ASYNC_CONSUMER = "Error async consumer";

    private final Session session;
    private final String queueName;

    public ConsumerJmsAsynchronousImpl(Connection connection, String queueName) {
        try {
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.queueName = queueName;

        } catch (JMSException e) {
            LOGGER.error(ERROR_CREATE_SESSION, e);
            throw new ConectionJmsException(ERROR_CREATE_SESSION);
        }
    }

    public void start() {
        try {
            MessageConsumer consumer = session.createConsumer(session.createQueue(queueName));
            consumer.setMessageListener(new ConsumerJmsListenerImpl());
        } catch (JMSException e) {
            LOGGER.error(ERROR_ASYNC_CONSUMER, e);
        }
    }
}
