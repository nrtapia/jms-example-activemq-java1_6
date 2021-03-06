package com.ntapia.notifications;

import com.ntapia.notifications.impl.ConsumerJmsSynchronousImpl;
import com.ntapia.notifications.impl.JmsConnectionFactory;
import com.ntapia.notifications.impl.PublisherJmsImpl;
import org.junit.Test;

import javax.jms.Connection;
import javax.jms.JMSException;

import static junit.framework.TestCase.assertEquals;

public class ConsumerJmsSynchronousImplTest {


    @Test
    public void testShouldReadMessageSuccessfully() {

        final String queueName = "queue.test2";
        final String message = "READ_MESSAGE";

        final Connection connection = JmsConnectionFactory.getInstance(
                UtilTest.TEST_USER_NAME, UtilTest.TEST_PASSWORD, UtilTest.TEST_BROKER_URL);
        try {
            Publisher publisher = new PublisherJmsImpl(connection);
            publisher.send(queueName, message);

            Consumer consumer = new ConsumerJmsSynchronousImpl(connection, 1000);
            String value = consumer.read(queueName);

            assertEquals(message, value);

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
