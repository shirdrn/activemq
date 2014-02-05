package org.shirdrn.activemq;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;

public class AsycReceiver {

	public void produce(String brokerURL, String user, String password, String destination) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection(user, password);
        connection.start();
        
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination dest = new ActiveMQTopic(destination);
        MessageProducer producer = session.createProducer(dest);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for(int i=1; i <= 100; i ++) {
            TextMessage msg = session.createTextMessage("xxxxxxxxxxx-" + i);
            msg.setIntProperty("id", i);
            producer.send(msg);
            System.out.println(String.format("Sent %d messages", i));
        }

        connection.close();
	}
	
	public void consume(String brokerURL, String user, String password, String destination) throws JMSException {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);
        Connection connection = factory.createConnection(user, password);
        connection.start();
        
        for (int i = 0; i < 10; i++) {
        	Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination dest = new ActiveMQTopic(destination);
            MessageConsumer consumer = session.createConsumer(dest);
            consumer.setMessageListener(new MessageListener() {

    			@Override
    			public void onMessage(Message message) {
    				try {
    					String id = "#" + Thread.currentThread().getId();
    					Thread.sleep(1000);
    					TextMessage m = (TextMessage) message;
    					System.out.println("Message processed: id = " + id + ", message = " + m.getText());
    				} catch (InterruptedException | JMSException e) {
    					e.printStackTrace();
    				}				
    			}
            	
            });
		}

//        connection.close();
	}
	
	public static void main(String[] args) {
		final String brokerURL = "tcp://localhost:61616";
		final String user = "";
		final String password = "";
		final String destination = "Q";
		
		final AsycReceiver rec = new AsycReceiver();
		Thread producer = new Thread() {
			public void run() {
				try {
					rec.produce(brokerURL, user, password, destination);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		};
		
		Thread consumer = new Thread() {
			@Override
			public void run() {
				try {
					rec.consume(brokerURL, user, password, destination);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		};
		
		consumer.start();
		producer.start();
		
	}
}
