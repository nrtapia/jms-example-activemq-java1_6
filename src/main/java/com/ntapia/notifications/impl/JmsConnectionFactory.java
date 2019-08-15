package com.ntapia.notifications.impl;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public final class JmsConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsConnectionFactory.class);

    private static final String BROKER_URL_REQUIRED = "Broker URL is required!";
    private static final String ERROR_CONNECT_TO = "Error to connect to: ";
    private static final String ERROR_CONNECT_ACTIVE_MQ = "Error to connect with ActiveMQ";

    private static Connection INSTANCE;

    public synchronized static Connection getInstance(String userName, String password, String brokerUrl) {
        if (brokerUrl == null || brokerUrl.trim().length() == 0) {
            throw new IllegalArgumentException(BROKER_URL_REQUIRED);
        }

        if (INSTANCE == null) {
            try {
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(userName, password, brokerUrl);
                INSTANCE = connectionFactory.createConnection();
                INSTANCE.start();
            } catch (JMSException e) {
                LOGGER.error(ERROR_CONNECT_TO + brokerUrl, e);
                throw new ConectionJmsException(ERROR_CONNECT_ACTIVE_MQ);
            }
        }

        return INSTANCE;
    }

}
