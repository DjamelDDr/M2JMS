/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.serveur;

import javax.jms.MessageProducer;

/**
 *
 * @author Djamel D
 * mm fonctionnement que le sender et donc un thread
 */
public class Reponse extends Thread{
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
