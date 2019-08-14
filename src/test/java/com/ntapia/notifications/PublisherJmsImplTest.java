package com.ntapia.notifications;

import com.ntapia.notifications.impl.JmsConnectionFactory;
import com.ntapia.notifications.impl.PublisherJmsImpl;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.JMSException;

public class PublisherJmsImplTest {

    @Test
    public void testShouldSendMessageSuccessfully() {

        String brokerUrl = "tcp://localhost:61616";
        String userName = "root";
        String password = "root";


        String target = "queue.test1";
        final String message = "Hello, world! ";

        final Connection connection = JmsConnectionFactory.getInstance(userName, password, brokerUrl);
        try {
            Publisher publisher = new PublisherJmsImpl(connection);
            publisher.send(target, message + " 1 " + System.currentTimeMillis());
            publisher.send(target, message + " 2 " + System.currentTimeMillis());
            publisher.send(target, message + " 3 " + System.currentTimeMillis());

        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }
}
