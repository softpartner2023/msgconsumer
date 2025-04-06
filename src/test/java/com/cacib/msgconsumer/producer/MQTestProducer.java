package com.cacib.msgconsumer.producer;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.*;

public class MQTestProducer {
    // Connection parameters (could be passed in or read from config)
    private static final String QMGR       = "QM1";
    private static final String CHANNEL    = "DEV.APP.SVRCONN";
    private static final String CONN_NAME  = "localhost(1414)";
    private static final String QUEUE_NAME = "DEV.QUEUE.1";
    private static final String USER       = "app";
    private static final String PASSWORD   = "passw0rd";

    public static void main(String[] args) {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            // 1. Create and configure the connection factory
            MQQueueConnectionFactory factory = new MQQueueConnectionFactory();
            factory.setQueueManager(QMGR);
            factory.setChannel(CHANNEL);
            factory.setConnectionNameList(CONN_NAME);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

            // 2. Create a Connection (with credentials) and start it
            connection = factory.createConnection(USER, PASSWORD);
            connection.start();

            // 3. Create a Session (non-transacted, auto ACK)
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 4. Create the target Queue and MessageProducer
            Queue queue = session.createQueue(QUEUE_NAME);
            producer = session.createProducer(queue);

            // 5. Create a TextMessage and send it
            TextMessage message = session.createTextMessage("Hello from JMS test!");
            producer.send(message);
            System.out.println("Sent message: " + message.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            // 6. Clean up resources
            try {
                if (producer != null) producer.close();
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException jmse) {
                jmse.printStackTrace();
            }
        }
    }
}
