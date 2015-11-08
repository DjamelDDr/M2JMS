/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.messages;

import java.util.Date;

/**
 *
 * @author Djamel D
 */
public class Message {
    private String contenu;
    private String villeEmission;
    private boolean msgGeolocalise;
    private Date dateEmission;

    /**
     * constructeur du message 
     */
    public Message(String contenu, String villeString, boolean msgGeolocalise){
        this.contenu = contenu;
        this.villeEmission = villeEmission;
        this.msgGeolocalise =msgGeolocalise;
        this.dateEmission = new Date();
    }
    
    /**
     * @return the contenu du msg
     */
    public String getContenu() {
        return contenu;
    }

    /**
     * @param contenu the contenu to set: on aura pas forcemment besoin
     */
    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    /**
     * @return the villeEmission
     */
    public String getVilleEmission() {
        return villeEmission;
    }

    /**
     * @param villeEmission the villeEmission to set
     */
    public void setVilleEmission(String villeEmission) {
        this.villeEmission = villeEmission;
    }

    /**
     * @return the msgGeolocalise
     */
    public boolean isMsgGeolocalise() {
        return msgGeolocalise;
    }

    /**
     * @return the dateEmission
     */
    public Date getDateEmission() {
        return dateEmission;
    }

    /**
     * @param dateEmission the dateEmission to set
     */
    public void setDateEmission(Date dateEmission) {
        this.dateEmission = dateEmission;
    }
    
    /**
     * 
     */
    
}
