/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.serveur;
import java.sql.Timestamp;
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
import javax.jms.TextMessage;
import projetjmsmodandr.client.Users;
import projetjmsmodandr.data.DataJDBC;
import projetjmsmodandr.messages.Tweet;
/**
 *
 * @author Djamel D
 */
public class Serveur {
    public static void main(String[] args) {
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String destName = null;
        Destination dest = null;
        int count = 1;
        Session session = null;
        MessageConsumer receiver3 = null;
        MessageConsumer receiUser = null;


        if (args.length < 1 || args.length > 2) {
            System.out.println("usage: Receiver <destination> [count]");
            System.exit(1);
        }

        destName = args[0];
        if (args.length == 2) {
            count = Integer.parseInt(args[1]);
        }

        try {
            context = new InitialContext();
            factory = (ConnectionFactory) context.lookup(factoryName);
            dest = (Destination) context.lookup(destName);
            connection = factory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            receiver3 = session.createConsumer(dest, "JMSType IN ('msg') ");
            receiUser = session.createConsumer(dest, "JMSType IN ('user') ");
            connection.start();
            Timestamp mydate;

            java.util.Date today = new java.util.Date();//recuperation de la date du jour
            mydate = new Timestamp(today.getTime());// recuperation du time actuelle

            DataJDBC db = new DataJDBC("maBddTwitter");
            
            
            while (true)  
            {
                System.out.println("AZERTY");
                Message message = receiUser.receive();
               
                ObjectMessage om = (ObjectMessage) message;
                
                Users t = (Users) om.getObject();
                /*
                System.out.println( db.insertUser("maBddTwitter", "maBddTwitter", "maBddTwitter", 
                        "maBddTwitter", mydate, "maBddTwitter"));
                */
                System.out.println("Received:    " + t.getLogin()+" pppp "+om.getJMSDestination());
                
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
