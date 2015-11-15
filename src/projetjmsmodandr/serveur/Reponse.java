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
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
/**
 *
 * @author Djamel D
 * mm fonctionnement que le sender et donc un thread
 */
public class Reponse extends Thread{
    
        Context context = null;
        ConnectionFactory factory = null;
        Connection connection = null;
        String factoryName = "ConnectionFactory";
        String destName = null;
        Destination dest = null;
        Session session = null;
    
    private MessageProducer sender;
    
    public Reponse(MessageProducer sender){
        this.sender = sender;
        
    }
    
    public void run(){
        /**
         * traitement de l'envoie de la reponse au message 
         */
    }
}
