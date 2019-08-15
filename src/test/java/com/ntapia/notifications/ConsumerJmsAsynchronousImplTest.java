package com.ntapia.notifications;

import com.ntapia.notifications.impl.ConsumerJmsAsynchronousImpl;
import com.ntapia.notifications.impl.ConsumerJmsSynchronousImpl;
import com.ntapia.notifications.impl.JmsConnectionFactory;
import com.ntapia.notifications.impl.PublisherJmsImpl;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.JMSException;

import static junit.framework.TestCase.assertEquals;

public class ConsumerJmsAsynchronousImplTest {

    @Test
    public void testShouldReadMessageSuccessfully() {

        final String queueName = "queue.test3";
        final String message = "READ_MESSAGE";

        final Connection connection = JmsConnectionFactory.getInstance(
                UtilTest.TEST_USER_NAME, UtilTest.TEST_PASSWORD, UtilTest.TEST_BROKER_URL);
        try {
            Publisher publisher = new PublisherJmsImpl(connection);
            for (int i = 0; i < 5; i++) {
                publisher.send(queueName, message + "-" + i);
            }

            ConsumerJmsAsynchronousImpl asynchronous = new ConsumerJmsAsynchronousImpl(connection, queueName);
            asynchronous.start();

            Thread.sleep(1000);

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
