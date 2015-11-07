/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.messages;

/**
 *
 * @author Djamel D
 */
public class Message {
    private String contenu;
    private String villeEmission;
    private boolean msgGeolocalise; 

    /**
     * constructeur du message 
     */
    public Message(String contenu, String villeString, boolean msgGeolocalise){
        this.contenu = contenu;
        this.villeEmission = villeEmission;
        this.msgGeolocalise =msgGeolocalise;
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
     * 
     */
    
}
