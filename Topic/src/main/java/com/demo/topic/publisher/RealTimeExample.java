package com.demo.topic.publisher;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.json.JSONObject;

public class RealTimeExample {

	public static void main(String[] args) {
		ConnectionFactory factory = new ActiveMQConnectionFactory("admin", "admin", 
				"tcp://localhost:61616");
		
		try {
			Connection connection = factory.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue("demo-chien");
			OblectTest oblectTest = new OblectTest("test1", "test2");
			
			JSONObject json = new JSONObject();
			json.put("test1", oblectTest.getTest1());
			json.put("test2", oblectTest.getTest2());
			
			TextMessage textMessage = session.createTextMessage(json.toString());
			
			MessageProducer producer = session.createProducer(destination);
			producer.send(textMessage);
			
			session.close();
			connection.close();
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
