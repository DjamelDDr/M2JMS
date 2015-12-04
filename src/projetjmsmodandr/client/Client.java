/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.client;
import java.sql.Timestamp;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.jms.JMSException;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import projetjmsmodandr.messages.Tweet;





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
        MessageConsumer receveurMsg = null;
        Boolean fin = false;
        if (args.length < 1 || args.length > 2) {
            System.out.println("usage: Sender <destination> [count]");
            System.exit(1);
        }

        destName = args[0];
        if (args.length == 2) {
            count = Integer.parseInt(args[1]);
        }

        try {
            //destNameFile = "queue1";
            // create the JNDI initial context.
            context = new InitialContext();

            // look up the ConnectionFactory
            factory = (ConnectionFactory) context.lookup(factoryName);
            // look up the Destination
            dest = (Destination) context.lookup(destName);          
            Destination destFileTmp =(Destination) context.lookup(destName);          
            // create the connection
            connection = factory.createConnection();
            // create the session
            session = connection.createSession(
                false, Session.AUTO_ACKNOWLEDGE);

            // create the sender
            sender = session.createProducer(dest);
            //l'envoi de message dans la file d'attente temporaire
            receveurMsg = session.createConsumer(destFileTmp, "JMSType IN ('T1','T2') ");
            
            // start the connection, to enable message sends
            connection.start();

            java.util.Date today = new java.util.Date();//recuperation de la date du jour
            Timestamp mydate = new Timestamp(today.getTime()); // recuperation du time actuelle
            
            Scanner sc = new Scanner(System.in);
		while (!fin) {
                    int choice;

                    System.out.println("+---------------------------+");
                    System.out.println("| 1 - CONNECTION            |");
                    System.out.println("| 2 - INSCRIPTION           |");
                    System.out.println("| 0 - Terminer              |");
                    System.out.println("+---------------------------+");

                    choice = sc.nextInt();
                    sc.nextLine(); // saute le retour à la ligne
                    switch (choice) {
			case 0://fin
                            sc.close();
                            fin = true;
                            break;
                        case 1:
                            String login = null, psw = null;
                            System.out.println("Login");
                            login = sc.nextLine();
                            System.out.println("Mot de passe");
                            psw = sc.nextLine();
                            
                            Users ulog = new Users(login, psw);
                            ObjectMessage objU = session.createObjectMessage(ulog);
                            objU.setJMSType("user");
                            sender.send(objU);
                            break;
                    }
                }
            
            
            
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
