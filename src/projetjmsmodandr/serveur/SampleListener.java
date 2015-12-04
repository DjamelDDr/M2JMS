/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.serveur;

import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
/**
 *
 * @author Djamel D
 */
public class SampleListener implements MessageListener{
    public SampleListener(){
        
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            ObjectMessage obj = (ObjectMessage) message;
            try {
                System.out.println("Received: "+ obj.getObject() );
            } catch (JMSException exception) {
                System.err.println("Failed to get message text: " + exception);
            }
        }
    }
}
