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
    private PreparedStatement requeteInsertHistoriqueSuiviSt = null;
    /**
     * requête préparées qui va contenir toutes les infos<p>
     * (squelette + valeurs)
     */ 
    private PreparedStatement requeteGetHistoriqueSuiviSt = null;
 
    
    private PreparedStatement requeteUpdateRetraitSt = null;


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
                
                
              	//creation Table MESSAGE 
	        s.execute("create table MESSAGE  ( " +
	            		"login VARCHAR( 256 ) , " +
                                "contenu VARCHAR( 500 ) , " +
                                "villeEmission VARCHAR( 256 ) , " +
	            		"geoActivee BOOLEAN , " +
                                "dateEmission TIMESTAMP , " +
	        			"CONSTRAINT pk_message PRIMARY KEY(login, dateEmission ) , " +
	        			"CONSTRAINT fk_message_user FOREIGN KEY(login ) REFERENCES USER(login ) )"
	        			);
                
                
                //creation Table HISTORIQUESUIVI 
	        s.execute("create table HISTORIQUESUIVI  ( " +
	            		"loginA VARCHAR( 256 ) , " +
                                "dateDebut TIMESTAMP , " +
                                "dateFin TIMESTAMP , " +
                                "loginB VARCHAR( 256 ) , " +
	        			"CONSTRAINT pk_historiquesuivi PRIMARY KEY(loginA, loginB ) , " +
                                        "CONSTRAINT fk_historiquesuivi_userA FOREIGN KEY(loginA ) REFERENCES USER(login ) , " +
	        			"CONSTRAINT fk_historiquesuivi_userB FOREIGN KEY(loginB ) REFERENCES USER(login ) )"
	        			);
	                        
                        
	    /*    	s.execute("create table VELO  ( " +
	        			"num_velo int ," +
	        			"CONSTRAINT pk_velo PRIMARY KEY(num_velo ), " +
	        			"CONSTRAINT nn_type_num_velo CHECK(num_velo  IS NOT NULL) )"
	        			);
	        	

	        	 //on ajoute des entrees des velos
	        	s.executeUpdate("insert into VELO values (1)");
	        	s.executeUpdate("insert into VELO values (2)");
	        	s.executeUpdate("insert into VELO values (3)");
	        	s.executeUpdate("insert into VELO values (4)");
	        	s.executeUpdate("insert into VELO values (5)");
	        	
	        	
   	
	        	s.execute("create table STATION  ( " +
	        			"num_station int ," +
	        			"isAtelier boolean, " +
	        			"capacite int," + 
	        			" CONSTRAINT pk_station PRIMARY KEY(num_station )," +
	        			" CONSTRAINT nn_type_num_station CHECK(num_station IS NOT NULL) )"
	        			);
	        	
	        	 //on ajoute des entrees des stations
	        	s.executeUpdate("insert into STATION values (1,0,5)");
	        	s.executeUpdate("insert into STATION values (2,0,5)");
	        	s.executeUpdate("insert into STATION values (3,0,5)");
	        	s.executeUpdate("insert into STATION values (4,1,5)");
	        	
	        	
	        	
	        */
	          /*   s.execute("create table EMPRUNTER  ( " +
	            		"login VARCHAR( 256 ) , " +
	            		// "num_velo int ," +
	        			"CONSTRAINT pk_station PRIMARY KEY(login, num_velo)," +
	        			"CONSTRAINT fk_emprunter_client FOREIGN KEY(login ) REFERENCES CLIENT(login ) ," +
	        			"CONSTRAINT fk_emprunter_velo FOREIGN KEY(num_velo ) REFERENCES VELO(num_velo ) ," +
	        			"CONSTRAINT nn_type_log_emprunte CHECK(login IS NOT NULL) ,"  +
						"CONSTRAINT nn_type_num_velo_emp CHECK(num_velo IS NOT NULL) )"
	        			); */

	        	
	        	// on retente la construction qui devrait desormais marcher
	        	requeteSelectUserLoginSt = conn.prepareStatement(requeteSelectUserLogin);
	        }
	        // construction des autres prepared statement
                requeteGetUserSt = conn.prepareStatement(requeteGetUser);
	        requeteDeleteUserSt = conn.prepareStatement(requeteDeleteUser);
                requeteUpdateUserSt = conn.prepareStatement(requeteUpdateUser);
                requeteInsertUserSt = conn.prepareStatement(requeteInsertUser);

                requeteSelectTestUserLoginSt = conn.prepareStatement(requeteSelectTestUserLogin);
                
                requeteGetHistoriqueSuiviSt = conn.prepareStatement(requeteGetHistoriqueSuivi);
                
                requeteInsertHistoriqueSuiviSt = conn.prepareStatement(requeteInsertHistoriqueSuivi);
                
                requeteSelectHistoriqueSuiviLoginsSt = conn.prepareStatement(requeteSelectHistoriqueSuiviLogins);
                                
    
		    
		  //  requeteInsertEmprunterSt = conn.prepareStatement(requeteInsertEmprunter);
		  //  requeteSelectTestEmprunterSt = conn.prepareStatement(requeteSelectTestEmprunter);
		   // requeteUpdateRetraitSt = conn.prepareStatement(requeteUpdateRetrait);
		    
		    
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
                System.out.println("Votre login :");
                log1 = sTestBDD.next(); //entree du login
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("login à suivre :");
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
                System.out.println("login du follower :");
                log2 = sTestBDD.next(); //entree du login du follower
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                
                System.out.println(obj.getHistoriqueSuivi(log1, log2));
                System.out.println("");
            */

            
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
				return null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
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
                    
                        retour = "login : " +login+ " déjà existant : ";
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
                        retour = "Creation de l'user  : "+login+ " effectuée";
                        
                        
                        return retour; 
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
         * @return 
         */
	public String getHistoriqueSuivi(String logA, String logB)
	{
		// TODO Auto-generated method stub
		System.out.println("Relation entre l'utilisateur : "+logA+" et l'utilisateur :" +logB);
		try {
			requeteGetHistoriqueSuiviSt.setString(1,logA);
                        requeteGetHistoriqueSuiviSt.setString(2, logB);
			ResultSet rs = requeteGetHistoriqueSuiviSt.executeQuery();
			if (rs.next()) {
				String retour = "Utilisateur = "+rs.getString(1) +"\n"+
                                                "Follower = "+rs.getString(4) +"\n"+
                                                "Depuis = "+rs.getTimestamp(2) +"\n"+
                                                "Jusqu'au = "+rs.getTimestamp(3);
				return retour;
			} else {
				return null;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
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
            System.out.println("Tentative de suivi part : "+logA+" de: "+logB);
            
                try {
                        requeteSelectHistoriqueSuiviLoginsSt.setString(1, logA);
                        requeteSelectHistoriqueSuiviLoginsSt.setString(2, logB);
                        ResultSet rs = requeteSelectHistoriqueSuiviLoginsSt.executeQuery();

                if ( rs.next()) {
                    
                        retour = "login : " +logA+ " suit déjà login : "+logB+" depuis le : "+ rs.getTimestamp(1);
	        	return retour;
                } else {
                        requeteInsertHistoriqueSuiviSt.setString(1,logA);
                        requeteInsertHistoriqueSuiviSt.setTimestamp(2, debut);
                        requeteInsertHistoriqueSuiviSt.setTimestamp(3, null);
                        requeteInsertHistoriqueSuiviSt.setString(4, logB);
                        requeteInsertHistoriqueSuiviSt.executeUpdate();
                        
                       System.out.println(1); 
                        retour = "Creation du suivi de l'user : "+logB+ " par l'user : "+logA+" effectué";
                        
                        
                        return retour; 
                }
                } catch (SQLException e) {
			e.printStackTrace();
			return "-1";
		}
            
        }

}
