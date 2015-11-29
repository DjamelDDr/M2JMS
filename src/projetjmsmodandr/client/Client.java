/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.client;
import java.sql.Timestamp;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.JMSException;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import projetjmsmodandr.messages.Tweet;


/**
 *
 * @author Djamel D
 */
public class Client {
       public static void main(String[] args) {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String destName = null;
        Destination dest = null;
        int count = 1;
        Session session = null;
        MessageProducer sender = null;
       // String text = "Message ";
        Queue queue = null;
       
        if (args.length < 1 || args.length > 2) {
            System.out.println("usage: Sender <destination> [count]");
            System.exit(1);
        }

        destName = args[0];
        if (args.length == 2) {
            count = Integer.parseInt(args[1]);
        }

        try {
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
        // look up the Destination
            dest = (Destination) context.lookup(destName);

            // create the connection
            connection = factory.createConnection();

            // create the session
            session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the sender
            sender = session.createProducer(dest);

/*ARRIVE ICI**/
            //QueueBrowser browser = session.createBrowser(queue);
            // start the connection, to enable message sends
            connection.start();

            java.util.Date today = new java.util.Date();//recuperation de la date du jour
            Timestamp mydate = new Timestamp(today.getTime()); // recuperation du time actuelle

            
            for (int i = 0; i < 6; ++i) {
                
                Tweet msg = new Tweet("contenuuuu","Toulouse",false);
                ObjectMessage objtweet = session.createObjectMessage(msg);
                //le type ici c'est son reseau
                objtweet.setJMSType("T1");
                sender.send(objtweet);
                System.out.println("Sent: " +  msg.getContenu());
                
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
