/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetjmsmodandr.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Scanner;

/**
 *
 * @author Hamza D
 */
public class DataJDBC {
    /**
     * variable de connection
     */
    private Connection conn;
    
  
     // squelettes des requetes sql sans les valeurs
    
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * vérifiant l'existance d'un login dans la TABLE USER
     */
    private static final String requeteSelectUserLogin = "select login from USER where login = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * récupérant toutes les infos d'un user dans la TABLE USER grâce à son login
     */
    private static final String requeteGetUser = "select login, mdp, nom, prenom, dateDerniereConnexion, villeReception from USER where login = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * supprimant un user de la TABLE USER en vérifiant son login et son mdp
     */
    private static final String requeteDeleteUser = "delete from USER where login = ? and mdp = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * mettant à jour le mdp d'un user dans la TABLE USER grâce à son login
     */
    private static final String requeteUpdateUser = "update USER set mdp = ? where login = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * mettant à jour la date de dernière connexion d'un user dans la TABLE USER grâce à son login
     */
    private static final String requeteUpdateUserConnexion = "update USER set dateDerniereConnexion = ? where login = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * créant un nouvel user avec toutes ses infos dans la TABLE USER
     */
    private static final String requeteInsertUser = "insert into USER values (?, ?, ?, ?, ?, ? )";
    /**
     * Squelette de la requête sql dans les valeurs<p>
     * testant la véracité du login et mdp dans la TABLE USER
     */
    private static final String requeteSelectTestUserLogin = "select login from USER where login = ? and mdp = ?";
   
    
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * vérifiant l'existance du trio (loginA + dateDebut + loginB) dans la TABLE HISTORIQUESUIVI
     */
    private static final String requeteSelectHistoriqueSuiviLogins = "select dateDebut from HISTORIQUESUIVI where loginA = ? and loginB = ?";
        /**
     * Squelette de la requête sql sans les valeurs<p>
     * vérifiant l'existance du trio (loginA + dateFin + loginB) dans la TABLE HISTORIQUESUIVI
     */
    private static final String requeteSelectHistoriqueSuiviLoginsDes = "select dateFin from HISTORIQUESUIVI where loginA = ? and loginB = ?";
    /**
     * Squelette de la requête sql dans les valeurs<p>
     * créant un nouvel historique de suivi entre 2 logins d'user et une date de début<p>
     * dans la TABLE HISTORIQUESUIVI
     */
    private static final String requeteInsertHistoriqueSuivi = "insert into HISTORIQUESUIVI values (?, ?, ?, ? )";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * récupérant toutes les infos d'historique de suivi entre 2 users<p>
     * dans la TABLE HISTORIQUESUIVI grâce aux 2 logins
     */
    private static final String requeteGetHistoriqueSuivi = "select loginA, dateDebut, dateFin, loginB from HISTORIQUESUIVI where loginA = ? and loginB = ?";      
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * ajoutant une date de fin d'historique de suivi <p>
     * dans la TABLE HISTORIQUESUIVI grâce aux 2 logins
     */
    private static final String requeteDelHistoriqueSuivi = "update HISTORIQUESUIVI set dateFin = ? where loginA = ? and loginB = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * récupérant les logins des users qui me suivent<p>
     * dans la TABLE HISTORIQUESUIVI grâce au login de l'user
     */
    private static final String requeteGetMesFollowers = "select loginB from HISTORIQUESUIVI where loginA = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * récupérant les logins des users qui me suivent<p>
     * dans la TABLE HISTORIQUESUIVI grâce au login de l'user
     */
    private static final String requeteGetPersIFollow = "select loginA from HISTORIQUESUIVI where loginB = ?";
    
    
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * créant un nouveau avec toutes ses infos dans la TABLE TWEETBD
     */
    private static final String requeteInsertTweetBD = "insert into TWEETBD (login, contenu, villeEmission, geoActivee, dateEmission) values (?, ?, ?, ?, ? )";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * récupérant toutes les infos d'un message dans la TABLE TWEETBD grâce à son login
     */
    private static final String requeteGetTweetBD = "select idTweet, login, contenu, villeEmission, geoActivee, dateEmission from TWEETBD where login = ?";
    /**
     * Squelette de la requête sql sans les valeurs<p>
     * récupérant toutes les infos d'un message dans la TABLE TWEETBD grâce à son login
     */
    private static final String requeteDelTweetBD = "delete from TWEETBD where login = ? and idTweet = ?";
    /**
     * Squelette de la requête sql dans les valeurs<p>
     * testant la présence du login la TABLE TWEETBD
     */
    private static final String requeteSelectTestTweetLogin = "select * from TWEETBD where login = ?";
    
    
    private static final String requeteInsertEmprunter = "insert into EMPRUNTER values (?, ?, ? )";

    
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteSelectUserLoginSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteGetUserSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteDeleteUserSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteUpdateUserSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteUpdateUserConnexionSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteInsertUserSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteSelectTestUserLoginSt = null;
    
    
     /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteSelectHistoriqueSuiviLoginsSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteSelectHistoriqueSuiviLoginsDesSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteInsertHistoriqueSuiviSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteGetHistoriqueSuiviSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteDelHistoriqueSuiviSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteGetMesFollowersSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteGetPersIFollowSt = null;
 
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteInsertTweetBDSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteGetTweetBDSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */
    private PreparedStatement requeteDelTweetBDSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteSelectTestTweetLoginSt = null;
    
    private PreparedStatement requeteUpdateRetraitSt = null;

//autoincrement pour les messages
    
    /**
     * Constructeur de la BDD
     * @param nomBD nom de la BDD
     */
    public DataJDBC(String nomBD) {
        
        try {
                // récupération du driver
		    Class.forName("org.h2.Driver");
                    
		    // création d'une connexion
		    conn = DriverManager.getConnection("jdbc:h2:"+nomBD+";IGNORECASE=TRUE", "sa", "");
                    
	        // On regarde si la table existe deja
	        try {
	        	// construction du prepared statement
	        	requeteSelectUserLoginSt = conn.prepareStatement(requeteSelectUserLogin);
	        } catch(Exception e) {
                    
	        	// sinon on l'a cree
	        	Statement s = conn.createStatement();
	        
                //creation table USER
         	s.execute("create table USER  ( " +
	        			"login VARCHAR( 256 ) , " +
	        			"mdp VARCHAR( 256 ) , " +
                                        "nom VARCHAR( 256 ) , " +
                                        "prenom VARCHAR( 256 ) , " +
                                        "dateDerniereConnexion TIMESTAMP , " +
	        			"villeReception VARCHAR( 256 ) ," +
                                            "CONSTRAINT pk_user PRIMARY KEY(login ) , " + 
                                            "CONSTRAINT nn_type_log_client CHECK(login IS NOT NULL) )"
	        		);
         	

        	//on ajoute des entrees des USER
        	s.executeUpdate("insert into USER values ('amz','ouioui','danpollo','hamza',{ts '2015-11-09 18:47:52.69'},'Paris')");
        	s.executeUpdate("insert into USER values ('kabal','ouais','moco','kevin',{ts '2015-11-01 10:34:04.69'},'Toulouse')");
        	s.executeUpdate("insert into USER values ('djadja','nonnon','drif','djamel',{ts '2015-02-14 06:08:36.69'},'Toulouse')");
                
                
              	//creation Table TWEETBD 
	        s.execute("create table TWEETBD  ( " +
	            		"idTweet IDENTITY , " +
                                "login VARCHAR( 256 ) , " +
                                "contenu VARCHAR( 500 ) , " +
                                "villeEmission VARCHAR( 256 ) , " +
	            		"geoActivee BOOLEAN , " +
                                "dateEmission TIMESTAMP , " +
	        			"CONSTRAINT fk_message_user FOREIGN KEY(login ) REFERENCES USER(login ) )"
	        			);
                
                
                //creation Table HISTORIQUESUIVI 
	        s.execute("create table HISTORIQUESUIVI  ( " +
	            		"loginA VARCHAR( 256 ) , " +
                                "dateDebut TIMESTAMP , " +
                                "dateFin TIMESTAMP , " +
                                "loginB VARCHAR( 256 ) , " +
	        			"CONSTRAINT pk_historiquesuivi PRIMARY KEY(loginA, loginB, dateDebut ) , " +
                                        "CONSTRAINT fk_historiquesuivi_userA FOREIGN KEY(loginA ) REFERENCES USER(login ) , " +
	        			"CONSTRAINT fk_historiquesuivi_userB FOREIGN KEY(loginB ) REFERENCES USER(login ) )"
	        			);
	                        
                        

	        	
	        	// on retente la construction qui devrait desormais marcher
	        	requeteSelectUserLoginSt = conn.prepareStatement(requeteSelectUserLogin);
	        }
	        // construction des autres prepared statement
                requeteGetUserSt = conn.prepareStatement(requeteGetUser);
	        requeteDeleteUserSt = conn.prepareStatement(requeteDeleteUser);
                requeteUpdateUserSt = conn.prepareStatement(requeteUpdateUser);
                requeteUpdateUserConnexionSt = conn.prepareStatement(requeteUpdateUserConnexion);
                requeteInsertUserSt = conn.prepareStatement(requeteInsertUser);

                requeteSelectTestUserLoginSt = conn.prepareStatement(requeteSelectTestUserLogin);
                
                requeteGetHistoriqueSuiviSt = conn.prepareStatement(requeteGetHistoriqueSuivi);
                
                requeteInsertHistoriqueSuiviSt = conn.prepareStatement(requeteInsertHistoriqueSuivi);
                
                requeteDelHistoriqueSuiviSt = conn.prepareStatement(requeteDelHistoriqueSuivi);
                
                requeteSelectHistoriqueSuiviLoginsSt = conn.prepareStatement(requeteSelectHistoriqueSuiviLogins);
                
                requeteSelectHistoriqueSuiviLoginsDesSt = conn.prepareStatement(requeteSelectHistoriqueSuiviLoginsDes);
                 
                requeteGetMesFollowersSt = conn.prepareStatement(requeteGetMesFollowers);
                
                requeteGetPersIFollowSt = conn.prepareStatement(requeteGetPersIFollow);
                
                requeteInsertTweetBDSt = conn.prepareStatement(requeteInsertTweetBD);
                
                requeteGetTweetBDSt = conn.prepareStatement(requeteGetTweetBD);
                
                requeteDelTweetBDSt = conn.prepareStatement(requeteDelTweetBD);
                
                requeteSelectTestTweetLoginSt = conn.prepareStatement(requeteSelectTestTweetLogin);
		    
		    
		    
		} catch(Exception e) {
			// il y a eu une erreur
			e.printStackTrace();
		}
    }
    
    	public void close() {
		try {
			conn.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
    
    	/**
         * Main BDD
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//nouvelle objet DataJDBC
		DataJDBC obj = new DataJDBC("maBddTwitter");
                
                //scanner pour les tests
                Scanner sTestBDD = new Scanner(System.in);
             
            
                //test getUser()
                System.out.println("test getUser()");
                String lo;
                System.out.println("login :");
                lo = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                
                System.out.println(obj.getUser(lo));
                System.out.println("");
              
            
            
            /*
              //test delUser()
                System.out.println("test delUser()");
                String lo1, md;
                System.out.println("login :");
                lo1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("mot de passe :");
                md = sTestBDD.next(); //entree du mdp
                sTestBDD.nextLine(); //saute le retour a la ligne
                
                obj.delUser(lo1,md);
            */
            /* 
                //test editUser()
                System.out.println("test editUser()");
                String lo1, mdpO, mdpN;
                System.out.println("login :");
                lo1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("ancien mot de passe :");
                mdpO = sTestBDD.next(); //entree de l'ancien mdp
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("nouveau mot de passe :");
                mdpN = sTestBDD.next(); //entree du nouveau mdp
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                System.out.println(obj.editUser(lo1,mdpO,mdpN));
                System.out.println("");
                System.out.println(obj.getUser(lo1));
            */ 

            /* 
                //test insertUser()
                System.out.println("test inserUser()");
                String lo1, mdp1, nom1, prenom1, villeR;
                Timestamp mydate;
                System.out.println("login :");
                lo1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("mot de passe :");
                mdp1 = sTestBDD.next(); //entree du mdp
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("nom :");
                nom1 = sTestBDD.next(); //entree du nom
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("prenom :");
                prenom1 = sTestBDD.next();//entree du prenom
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("ville de reception :");
                villeR = sTestBDD.next();//entree de la ville de reception
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                
                java.util.Date today = new java.util.Date();//recuperation de la date du jour
                mydate = new Timestamp(today.getTime());// recuperation du time actuelle
                
                System.out.println(obj.insertUser(lo1, mdp1, nom1, prenom1, mydate, villeR));
                System.out.println("");
                System.out.println(obj.getUser(lo1));
            */
            /*    
                //test insertHistoriqueSuivi()
                System.out.println("test inserHistoriqueSuivi()");
                String log1, log2;Timestamp maintenant;
                System.out.println("Login à suivre :");
                log1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("Votre login  :");
                log2 = sTestBDD.next(); //entree du login à suivre
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                
                java.util.Date today = new java.util.Date();//recuperation de la date du jour
                maintenant = new Timestamp(today.getTime());// recuperation du time actuelle
                
                System.out.println(obj.insertHistoriqueSuivi(log1, log2, maintenant));
                System.out.println("");
            */
            /*    
                //test getHistoriqueSuivi()
                System.out.println("test getHistoriqueSuivi()");
                String log1, log2;
                System.out.println("Votre login :");
                log1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("Login du follower :");
                log2 = sTestBDD.next(); //entree du login du follower
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                
                System.out.println(obj.getHistoriqueSuivi(log1, log2));
                System.out.println("");
            */
            /*   
                //test delHistoriqueSuivi()
                System.out.println("test delHistoriqueSuivi()");
                String log1, log2;Timestamp maintenant;
                System.out.println("Login à ne plus suivre :");
                log1 = sTestBDD.next(); //entree du login à ne plus suivre
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("Votre login :");
                log2 = sTestBDD.next(); //entree de votre login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                
                
                java.util.Date today = new java.util.Date();//recuperation de la date du jour
                maintenant = new Timestamp(today.getTime());// recuperation du time actuelle
                
                System.out.println(obj.delHistoriqueSuivi(log1, log2, maintenant));
                System.out.println("");
            */
            
                
            /*
                //test insertTweetBD()
                System.out.println("test inserTweetBD()");
                String lo1, cntnu, vEmiss, repGeo;
                Boolean geo = false;
                Timestamp mydate;
                System.out.println("login :");
                lo1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("contenu : (max 500 carac) ");
                cntnu = sTestBDD.nextLine(); //entree du mess
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("ville d'emision :");
                vEmiss = sTestBDD.next(); //entree de la ville d'emission
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("geolocalisation :(Y/N) ??");
                repGeo = sTestBDD.next();//entree du prenom
                sTestBDD.nextLine(); //saute le retour a la ligne
                    if (repGeo.equalsIgnoreCase("Y"))
                    {
                           geo = true;
                    }else
                        {
                            geo = false;
                        }
                    
                System.out.println("");
                
                java.util.Date today = new java.util.Date();//recuperation de la date du jour
                mydate = new Timestamp(today.getTime());// recuperation du time actuelle
                
                System.out.println(obj.insertTweetBD(lo1, cntnu, vEmiss, geo, mydate));
                System.out.println("");
            */
            /*
                //test getTweetBD()
                System.out.println("test getTweetBD()");
                String loT;
                System.out.println("login :");
                loT = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                
                System.out.println(obj.getTweetBD(loT));
                System.out.println("");
            */
            /*
              //test delTweetBD()
                System.out.println("test delTweetBD()");
                String loTd;int num;
                System.out.println("login :");
                loTd = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("numero du message à supprimer :");
                num = sTestBDD.nextInt(); //entree du num
                sTestBDD.nextLine(); //saute le retour a la ligne
                
                obj.delTweet(loTd,num);
            */
               
            /*
                //test getMesFollowers()
                System.out.println("test getMesFollowers()");
                String lFol;
                System.out.println("Votre login :");
                lFol = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                
                System.out.println(obj.getMesFollowers(lFol));
                System.out.println("");
            */
            /*
                //test getPersIFollow()
                System.out.println("test getPersIFollow()");
                String lpFol;
                System.out.println("Votre login :");
                lpFol = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                
                System.out.println(obj.getPersIFollow(lpFol));
                System.out.println("");
            */
             
                //test connexion()
                System.out.println("test connexion()");
                String logCox, mdpO;
                System.out.println("login :");
                logCox = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("mot de passe :");
                mdpO = sTestBDD.next(); //entree du mdp
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                System.out.println(obj.connexion(logCox,mdpO));
                System.out.println("");
                System.out.println(obj.getUser(logCox));
           
                obj.close();
                
                

	}
        /**
        * Retournes les infos d'un user
        * @param login identifiant de l'utilisateur
        * @return null ou retour  - les infos d'un user
        **/
	public String getUser(String login)
	{
		// TODO Auto-generated method stub
		System.out.println("L'utilisateur est: "+login);
		try {
			requeteGetUserSt.setString(1,login);
			ResultSet rs = requeteGetUserSt.executeQuery();
			if (rs.next()) {
				String retour = "login = "+rs.getString(1) +"    "+"mdp = "+rs.getString(2)+"\n"+
                                                "nom = "+rs.getString(3) +"    "+"prenom = "+rs.getString(4)+ "\n"+
                                                "date de derniere connexion = "+rs.getTimestamp(5)+"    "+"ville de reception = "+rs.getString(6);
				return retour;
			} else {
                                System.out.println("L'utilisateur est inconnu !!!");
				return "0";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return "-1";
		}
	}
        
        /**
        * Supprimes un user
        * @param login identifiant d'un user
        * @param mdp mot de passe d'un user
        * @return false - login et/ou mdp n'existe pas ou true
        **/
        public boolean delUser(String login,String mdp)
	{

		System.out.println("Tentative de suppression de l'user : "+login+ " Mdp :"+mdp);
		
		try {
			requeteSelectTestUserLoginSt.setString(1,login);
			requeteSelectTestUserLoginSt.setString(2,mdp);
			
			ResultSet rs = requeteSelectTestUserLoginSt.executeQuery();
			
	        if (! rs.next()) {
	        	
	        	System.out.println("login ou/et mdp n'existe pas : " +login+ " Mdp :"+mdp);
	        	return false;
	        } else {
	        	requeteDeleteUserSt.setString(1, login);
	        	requeteDeleteUserSt.setString(2, mdp);
	        	requeteDeleteUserSt.executeUpdate();
	        	System.out.println(" Suppression de l'user : "+login+ " effectuée" );
	        	return true;
	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
        
        /**
         *Modifie le mot de passe d'un user
        * @param login identifiant d'un user
        * @param mdpOld mot de passe d'un user
        * @param mdpNew nouveau mot de passe d'un user
         * @return  retour - login et/ou mdp n'existe pas ou  les infos modifiee d'un user
         */
        public String editUser(String login, String mdpOld, String mdpNew)
        {
                String retour;
                System.out.println("Tentative de modification de l'user : "+login+ " avec mdp : "+mdpNew);
                
                try {
			requeteSelectTestUserLoginSt.setString(1,login);
			requeteSelectTestUserLoginSt.setString(2,mdpOld);
			
			ResultSet rs = requeteSelectTestUserLoginSt.executeQuery();
			
	        if (! rs.next()) {
	        	
	        	retour = "login ou/et mdp n'existe pas : " +login+ " Mdp : "+mdpOld;
	        	return retour;
	        } else {
                        requeteUpdateUserSt.setString(1, mdpNew);
                        requeteUpdateUserSt.setString(2, login);
                        requeteUpdateUserSt.executeUpdate();
                
                       System.out.println(1); 
                        retour = "Modification du mot de passe de l'user  : "+login+ " effectuée";
                        
                        
	        	return retour;
	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return "-1";
		}

            
        }
        
        /**
         * Insert un nouvel user
         * @param login identifiant d'un user
         * @param mdp mot de passe d'un user
         * @param nom nom d'un user
         * @param prenom prenom d'un user
         * @param dtDerConx date de dernière connexion
         * @param villeRecx ville de reception
         * @return retour - login déja existant ou  les infos du nouvel user
         */
        public String insertUser(String login, String mdp, String nom, String prenom, Timestamp dtDerConx, String villeRecx)
        {
                String retour;
                System.out.println("Tentative de creation de l'user : "+login);

                try {
                        requeteSelectUserLoginSt.setString(1, login);

                        ResultSet rs = requeteSelectUserLoginSt.executeQuery();

                if ( rs.next()) {
                    
                        retour = "0";
	        	return retour;
                } else {
                        requeteInsertUserSt.setString(1,login);
                        requeteInsertUserSt.setString(2, mdp);
                        requeteInsertUserSt.setString(3, nom);
                        requeteInsertUserSt.setString(4, prenom);
                        requeteInsertUserSt.setTimestamp(5, dtDerConx);
                        requeteInsertUserSt.setString(6, villeRecx);
                        requeteInsertUserSt.executeUpdate();
                        
                       System.out.println(1); 
                        retour = "1";
                        
                        
                        return retour; 
                }
                } catch (SQLException e) {
			e.printStackTrace();
			return "-1";
		}
            
        }
        
        /**
        * Retournes les infos d'un user
        * @param login identifiant de l'utilisateur
        * @param mdp
        * @return null ou retour  - ok
        **/
	public String connexion(String login, String mdp)
	{
		// TODO Auto-generated method stub
                String retour;
                Timestamp myDate;
                System.out.println("Tentative de connexion de l'user : "+login+ " avec mdp : "+mdp);
                
                try {
			requeteSelectTestUserLoginSt.setString(1,login);
			requeteSelectTestUserLoginSt.setString(2,mdp);
			
			ResultSet rs = requeteSelectTestUserLoginSt.executeQuery();
			
	        if (! rs.next()) {
	        	
	        	retour = "login ou/et mdp n'existe pas : " +login+ " Mdp : "+mdp;
                        System.out.println(retour);
	        	return "0";
	        } else {
                    
                java.util.Date today = new java.util.Date();//recuperation de la date du jour
                myDate = new Timestamp(today.getTime());// recuperation du time actuelle
                
                        requeteUpdateUserConnexionSt.setTimestamp(1, myDate);
                        requeteUpdateUserConnexionSt.setString(2, login);
                        requeteUpdateUserConnexionSt.executeUpdate();
                
                       System.out.println(1); 
                        retour = "Connexion de  : "+login+ " enregistrée";
                        
                        System.out.println(retour);
	        	return "1";
	        	
	        }
		} catch (SQLException ex) {
			ex.printStackTrace();
			return "-1";
		}
	}
        
        /**
         * 
         * @param logA
         * @param logB
         * @return 
         */
	public String getHistoriqueSuivi(String logA, String logB)
	{
		// TODO Auto-generated method stub
                String retour=""; 
		System.out.println("Relation entre l'utilisateur : "+logA+" et l'utilisateur :" +logB);
		try {
			requeteGetHistoriqueSuiviSt.setString(1,logA);
                        requeteGetHistoriqueSuiviSt.setString(2, logB);
			ResultSet rs = requeteGetHistoriqueSuiviSt.executeQuery();
                        
   
                        
			while (rs.next()) {
                            
                                     retour  += "Utilisateur = "+rs.getString(1) +"\n"+
                                                "Follower = "+rs.getString(4) +"\n"+
                                                "Depuis = "+rs.getTimestamp(2) +"\n"+
                                                "Jusqu'au = "+rs.getTimestamp(3)+"\n"+
                                                "  ---------- "+"\n";
				
                                    
			}
                        if(retour.equals("")){
                            
                            return null;
                           
                        }else{
                            return retour;
                            
                        }        
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
                
	}
        
        /**
         * 
         * @param logA identifiant d'un user
         * @return 
         */
	public String getMesFollowers(String logA)
	{
		// TODO Auto-generated method stub
                String users=","; 
		System.out.println("Followers de l'utilisateur : "+logA);
		try {
			requeteGetMesFollowersSt.setString(1,logA);
                        ResultSet rs = requeteGetMesFollowersSt.executeQuery();
                        
   
                        
			while (rs.next()) {
                            
                                     users  += rs.getString(1) +",";
				
                                    
			}
                        if(users.equals("")){
                            System.out.println("Vous N'avez Aucun Followers");
                            return "0";
                           
                        }else{
                            return users;
                            
                        }        
			//}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return "-1";
		}
                
	}
        
        /**
         * 
         * @param logB identifiant d'un user
         * @return 
         */
	public String getPersIFollow(String logB)
	{
		// TODO Auto-generated method stub
                String users=","; 
		System.out.println("Personnes que: "+logB+ " follow");
		try {
			requeteGetPersIFollowSt.setString(1,logB);
                        ResultSet rs = requeteGetPersIFollowSt.executeQuery();
                        
   
                        
			while (rs.next()) {
                            
                                     users  += rs.getString(1) +",";
				
                                    
			}
                        if(users.equals("")){
                            System.out.println("Vous Ne Suivez Personne pour l'instant");
                            return "0";
                           
                        }else{
                            return users;
                            
                        }        
			//}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return "-1";
		}
                
	}
        
        /**
         * 
         * @param logA
         * @param logB
         * @param debut
         * @return 
         */
        public String insertHistoriqueSuivi(String logA, String logB, Timestamp debut)
        {
            String retour;
            System.out.println("Tentative de suivi part : "+logB+" de: "+logA);
            
                try {
                        requeteSelectHistoriqueSuiviLoginsSt.setString(1, logA);
                        requeteSelectHistoriqueSuiviLoginsSt.setString(2, logB);
                        ResultSet rs = requeteSelectHistoriqueSuiviLoginsSt.executeQuery();
                        
                        requeteSelectHistoriqueSuiviLoginsDesSt.setString(1, logA);
                        requeteSelectHistoriqueSuiviLoginsDesSt.setString(2, logB);
                        ResultSet rs1 = requeteSelectHistoriqueSuiviLoginsDesSt.executeQuery();

                if ( rs.next()) {
                    rs1.next();
                    if (rs1.getTimestamp(1) != null)
                    {
                        requeteInsertHistoriqueSuiviSt.setString(1,logA);
                        requeteInsertHistoriqueSuiviSt.setTimestamp(2, debut);
                        requeteInsertHistoriqueSuiviSt.setNull(3, 93);
                        requeteInsertHistoriqueSuiviSt.setString(4, logB);                        
                        requeteInsertHistoriqueSuiviSt.executeUpdate();
                        
                        retour = "(Réabonnement)Creation du suivi de l'user : "+logA+ " par l'user : "+logB+" effectué";
                        System.out.println(retour); 
                        return "2"; 
                    }
                    
                        retour = "login : " +logB+ " suit déjà login : "+logA+" depuis le : "+ rs.getTimestamp(1);
	        	System.out.println(retour); 
                        return "0";
                } else {
                    
                        requeteInsertHistoriqueSuiviSt.setString(1,logA);
                        requeteInsertHistoriqueSuiviSt.setTimestamp(2, debut);
                        requeteInsertHistoriqueSuiviSt.setNull(3, 93);
                        requeteInsertHistoriqueSuiviSt.setString(4, logB);
                        requeteInsertHistoriqueSuiviSt.executeUpdate();
                         
                        retour = "Creation du suivi de l'user : "+logA+ " par l'user : "+logB+" effectué";
                        System.out.println(retour); 
                        return "1"; 
                        
                        
                        
                }
                } catch (SQLException e) {
			e.printStackTrace();
			return "-1";
		}
            
        }

        /**
         * 
         * @param logA
         * @param logB
         * @param fin
         * @return 
         */
        public String delHistoriqueSuivi(String logA, String logB, Timestamp fin)
        {
            String retour;
            System.out.println("Tentative de désabonement part : "+logB+" à: "+logA);
            
                try {
                        requeteSelectHistoriqueSuiviLoginsSt.setString(1, logA);
                        requeteSelectHistoriqueSuiviLoginsSt.setString(2, logB);
                        ResultSet rs = requeteSelectHistoriqueSuiviLoginsSt.executeQuery();
                        
                        requeteSelectHistoriqueSuiviLoginsDesSt.setString(1, logA);
                        requeteSelectHistoriqueSuiviLoginsDesSt.setString(2, logB);
                        ResultSet rs1 = requeteSelectHistoriqueSuiviLoginsDesSt.executeQuery();

                if ( rs.next()) {
                    
                    rs1.next();
                    
                    if (rs1.getTimestamp(1) != null)
                    {
                        retour = "login : " +logB+ " est déjà désabonné au login : "+logA;
                        System.out.println(retour);
	        	return "0";
                    }
                    
                        requeteDelHistoriqueSuiviSt.setTimestamp(1, fin);
                        requeteDelHistoriqueSuiviSt.setString(2, logA);
                        requeteDelHistoriqueSuiviSt.setString(3, logB);
                        requeteDelHistoriqueSuiviSt.executeUpdate();
                        
                         
                        retour = "Desabonnement à l'user : "+logA+ " par l'user : "+logB+" effectué";
                        System.out.println(retour);
                        return "1"; 
                    
                        
                } else {
                        
                        retour = "login : " +logB+ " ne suit pas encore login : "+logA;
                        System.out.println(retour);
	        	return "0";
                }
                } catch (SQLException e) {
			e.printStackTrace();
			return "-1";
		}
            
        }
        
        
        
        /**
         * Insert un nouveau message
         * @param login identifiant d'un user
         * @param contenu le texte du message
         * @param villeEmission ville d'emission du message
         * @param geoActivee option de geolocalisation du message
         * @param dtEmission date de d'emission du message
         * @return retour - login déja existant ou  les infos du nouvel user
         */
        public String insertTweetBD(String login, String contenu, String villeEmission, Boolean geoActivee, Timestamp dtEmission)
        {
                String retour;
                System.out.println("Tentative d'enregistrement du tweet de : "+login);

                try {
                        requeteInsertTweetBDSt.setString(1, login);
                        requeteInsertTweetBDSt.setString(2, contenu);
                        requeteInsertTweetBDSt.setString(3, villeEmission);
                        requeteInsertTweetBDSt.setBoolean(4, geoActivee);
                        requeteInsertTweetBDSt.setTimestamp(5, dtEmission);
                        requeteInsertTweetBDSt.executeUpdate();
 
    
                       System.out.println("Tweet Enregistré"); 
                        retour = "1";
                        
                        
                        return retour;
                        
     
                   // retour = "0";
	        	//return retour;
                
                } catch (SQLException e) {
			e.printStackTrace();
			return "-1";
		}
            
        }
        
        /**
         * 
         * @param logA
         * @return 
         */
	public String getTweetBD(String logA)
	{
		// TODO Auto-generated method stub
                String retour=""; 
		System.out.println("Recupération des tweet de : "+logA);
		try {
			requeteGetTweetBDSt.setString(1,logA);
			ResultSet rs = requeteGetTweetBDSt.executeQuery();
                        
   
                        
			while (rs.next()) {
                            
                                     retour  += "Login = "+rs.getString(2) +"\n"+
                                                "Tweet N° = "+rs.getInt(1) +"\n"+
                                                "Contenu = "+rs.getString(3) +"\n"+
                                                "Ville d'Emission = "+rs.getString(4) +"\n"+
                                                "Géolocalisé = "+rs.getBoolean(5) +"\n"+
                                                "Date d'Emission = "+rs.getTimestamp(6)+"\n"+
                                                "  ---------- "+"\n";
				
                                    //System.out.println(retour);
                                     //return retour;
                                    
			} //else {
                        if(retour.equals("")){
                            System.out.println("Aucun tweet Envoyé");
                            return null;
                           
                        }else{
                            return retour;
                            
                        }        
			//}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
                //return retour;
	}
        
        /**
        * Supprimes un tweet
        * @param login identifiant d'un user
        * @param nbMess numéro du message
        * @return false - login et/ou mdp n'existe pas ou true
        **/
        public boolean delTweet(String login,int nbMess)
	{

		System.out.println("Tentative de suppression du tweet : "+nbMess+ " de l'user :"+login);
		
		try {
			requeteSelectTestTweetLoginSt.setString(1,login);
			
			ResultSet rs = requeteSelectTestTweetLoginSt.executeQuery();
			
	        if (! rs.next()) {
	        	
	        	System.out.println("L'utilisateur : " +login+ " n'as pas encore envoyer de message :");
	        	return false;
	        } else {
	        	requeteDelTweetBDSt.setString(1, login);
	        	requeteDelTweetBDSt.setInt(2, nbMess);
	        	requeteDelTweetBDSt.executeUpdate();
	        	System.out.println("Suppression du message effectuée" );
	        	return true;
	        	
	        }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
