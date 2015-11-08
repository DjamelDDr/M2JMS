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
    private Date dateDebut;
    private Date dateFin;
    

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
    
    /**
     * connection a l'appli
     * @param login
     * @param motDePasse
     * @return 
     */
    public String connection(String login, String motDePasse){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * se deconnecter
     * @param login
     * @return 
     */
    public String decconnection(String login){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * voir l'historique des tweets, des personnes suivies,.. 
     * @return 
     */
    public String voirHistorique(){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * rechercher une personne par son login
     * @param login
     * @return 
     */
    public String recherche(String login){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * s'inscrire au service 
     * @param login
     * @return 
     */
    public String sinscrire(String login){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * diffuser un tweet
     * @return 
     */
    public String diffuser(){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * s'abonner a qlq1
     * @param login
     * @return 
     */
    public String suivre(String login){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
    
    /**
     * se desabonner d'un abonnement
     * @param login
     * @return 
     */
    public String desabonner(String login){
        /**
         * reste le corps et connection a la 
         */
       return "manque la connection a la base ";
    }
}
