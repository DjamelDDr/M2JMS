/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.serveur;
	import javax.naming.Context;
	import javax.naming.InitialContext;
	import javax.naming.NamingException;
	import javax.jms.JMSException;
	import javax.jms.Message;
	import javax.jms.Destination;
	import javax.jms.Connection;
	import javax.jms.ConnectionFactory;
	import javax.jms.ObjectMessage;
	import javax.jms.Session;
	import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
	
import projetjmsmodandr.messages.Tweet;
/**
 *
 * @author Djamel D
 */
public class Serveur {
    
     static String destName = "queue1";
    public static void main(String[] args) {       
	        Context context = null;
	        ConnectionFactory factory = null;
	        Connection connection = null;
	        String factoryName = "ConnectionFactory";
	       
	        Destination dest = null;
	        int count = 1;
	        Session session = null;
	        MessageConsumer receiver3 = null;
                MessageProducer sender = null;   
	        /*if (args.length < 1 || args.length > 2) {
	            System.out.println("usage: Receiver <destination> [count]");
	            System.exit(1);
	        }

	        destName = args[0];
	        if (args.length == 2) {
	            count = Integer.parseInt(args[1]);
	        }
                */
	        try {
	            // create the JNDI initial context
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

	            // create the receiver
	            //receiver = session.createConsumer(dest);
	            
	            // create the receiver avec le type
	            receiver3 = session.createConsumer(dest);

	            // start the connection, to enable message receipt
	            connection.start();
                    
	            while (true)  
	            {       
                        
		            Message message = receiver3.receive();
                            
		            ObjectMessage om = (ObjectMessage) message;
                            
		            Tweet t = (Tweet) om.getObject();
		            
		            System.out.println("Received: " + t.getContenu());
                            
                            
                            //Message response = session.createMessage();
                            //response.setJMSCorrelationID(om.getJMSCorrelationID());
                            
                            //sender.send(om.getJMSReplyTo(), response);

                            //sender = session.createProducer(dest);
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
