/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.utilisateurs;

import java.util.Date;

/**
 *
 * @author Djamel D
 */
public class Users {
    private String nom;
    private String prenom;
    private String login;
    private String motDePasse;
    private Date derniereConnexion;
    private String villeReceprion;
    
    public Users(String nom,String prenom,String login,String motDePasse,String villeReceprion){
        this.nom= nom;
        this.prenom=prenom;
        this.login = login;
        this.motDePasse = motDePasse;
        this.derniereConnexion = new Date();
        this.villeReceprion = villeReceprion;
    }
    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the motDePasse
     */
    public String getMotDePasse() {
        return motDePasse;
    }

    /**
     * @param motDePasse the motDePasse to set
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    /**
     * @return the derniereConnexion
     */
    public Date getDerniereConnexion() {
        return derniereConnexion;
    }

    /**
     * @param derniereConnexion the derniereConnexion to set
     */
    public void setDerniereConnexion(Date derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

    /**
     * @return the villeReceprion
     */
    public String getVilleReceprion() {
        return villeReceprion;
    }

    /**
     * @param villeReceprion the villeReceprion to set
     */
    public void setVilleReceprion(String villeReceprion) {
        this.villeReceprion = villeReceprion;
    }
    
    // mettre les autres méthodes 
}
