/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import projetjmsmodandr.client.Users;
import projetjmsmodandr.data.DataJDBC;

import projetjmsmodandr.messages.Tweet;
/**
 *
 * @author Djamel D
 */
public class Serveur{

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
        MessageConsumer listener = null;
        
        BufferedReader waiter = null;
        
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
     
            listener = session.createConsumer(dest);
            //receiver3 = session.createConsumer(dest, "JMSType IN ('msg') ");
            listener.setMessageListener(new SampleListener());
            connection.start();
            Timestamp mydate;

            java.util.Date today = new java.util.Date();//recuperation de la date du jour
            mydate = new Timestamp(today.getTime());// recuperation du time actuelle

            //supprimer fichier H2 ds racine projet
           // DataJDBC db = new DataJDBC("maBddTwitter");
            
            
          
            waiter = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("            "+waiter.readLine());
            
        }catch (IOException exception) {
            exception.printStackTrace();
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
