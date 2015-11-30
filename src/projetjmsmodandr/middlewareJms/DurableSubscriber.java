/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.middlewareJms;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.jms.Destination;
import javax.jms.ObjectMessage;
import projetjmsmodandr.messages.Tweet;
/**
 *
 * @author Djamel D
 */
public class DurableSubscriber {
/**
 * converse avec les users et le serv 
 * 
 */
    public static void main(String[] args) {
        //    System.out.println("Argument 1 : "+args[0]+", argument 2 : "+args[1]);
        //System.out.println("icic"); 
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String topicName = null;
        Topic topic = null;
        int count = 1;
        Session session = null;
        TopicSubscriber subscriber = null;
        String subscriptionName = "TWEETER";
        
        if (args.length < 1 || args.length > 2) {
            System.out.println("usage: DurableSubscriber <topic> [count]");
            System.exit(1);
        }

        topicName = args[0];
        if (args.length == 2) {
            count = Integer.parseInt(args[1]);
        }

        try {
   
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);

            // look up the topic
            topic = (Topic) context.lookup(topicName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the durable subscriber
            subscriber = session.createDurableSubscriber(
                topic, subscriptionName);

            // start the connection, to enable message receipt
            connection.start();

            for (int i = 0; i < count; ++i) {
                    
                Message message = subscriber.receive();
                if (message instanceof ObjectMessage) {
                    ObjectMessage om = (ObjectMessage) message;
                    Tweet tw = (Tweet)om.getObject();
                    
                    System.out.println("Contenu :"+ tw.getContenu());
                } else if (message != null) {
                    System.out.println("Received non object message");
                }
            }
        } catch (JMSException exception) {
            exception.printStackTrace();
        } catch (NamingException exception) {
            exception.printStackTrace();
        } finally {
            // close the context
            if (context != null) {
                try {
                    context.close();
                } catch (NamingException exception) {
                    exception.printStackTrace();
                }
            }

            // close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }
    
}
