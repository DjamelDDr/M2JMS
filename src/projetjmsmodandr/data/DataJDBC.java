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

import java.util.Scanner;

/**
 *
 * @author Hamza D
 */
public class DataJDBC {
    
    private Connection conn;
    
    //squelettes des requetes sql sans les valeurs
    private static final String requeteSelectUserLogin = "select login from USER where login = ?";
    
    private static final String requeteGetUser = "select login, mdp, nom, prenom, dateDerniereConnexion, villeReception from USER where login = ?";
    private static final String requeteDeleteUser = "delete from USER where login = ? and mdp = ?";
    private static final String requeteUpdateUser = "update USER set mdp = ? where login = ?";
    
    private static final String requeteSelectTestUserLogin = "select login from USER where login = ? and mdp = ?";
    
    private static final String requeteInsertUser = "insert into USER values (?, ?, ?, ?, ?, ? )";

    
    
    private static final String requeteInsertEmprunter = "insert into EMPRUNTER values (?, ?, ? )";

    //requetes preparees qui va contenir tout les infos (squelletes + valeurs)
    private PreparedStatement requeteSelectUserLoginSt = null;
    
    private PreparedStatement requeteGetUserSt = null;
    private PreparedStatement requeteDeleteUserSt = null;
    private PreparedStatement requeteUpdateUserSt = null;
    
    private PreparedStatement requeteSelectTestUserLoginSt = null;
    
    private PreparedStatement requeteInsertUserSt = null;
 
    
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
                                        "dateDerniereConnexion DATE , " +
	        			"villeReception VARCHAR( 256 ) ," +
                                            "CONSTRAINT pk_user PRIMARY KEY(login ) , " + 
                                            "CONSTRAINT nn_type_log_client CHECK(login IS NOT NULL) )"
	        		);
         	

        	//on ajoute des entrees des USER
        	s.executeUpdate("insert into USER values ('amz','ouioui','danpollo','hamza','2015-11-09','Paris')");
        	s.executeUpdate("insert into USER values ('kabal','ouais','moco','kevin','2015-11-01','Toulouse')");
        	s.executeUpdate("insert into USER values ('djadja','nonnon','drif','djamel','2015-02-14','Toulouse')");
                
                
              	//creation Table MESSAGE 
	        s.execute("create table MESSAGE  ( " +
	            		"login VARCHAR( 256 ) , " +
                                "contenu VARCHAR( 500 ) , " +
                                "villeEmission VARCHAR( 256 ) , " +
	            		"geoActivee BOOLEAN , " +
                                "dateEmission DATE , " +
	        			"CONSTRAINT pk_message PRIMARY KEY(login, dateEmission ) , " +
	        			"CONSTRAINT fk_message_user FOREIGN KEY(login ) REFERENCES USER(login ) )"
	        			);
                
                
                //creation Table HISTORIQUESUIVI 
	        s.execute("create table HISTORIQUESUIVI  ( " +
	            		"loginA VARCHAR( 256 ) , " +
                                "dateDebut DATE , " +
                                "dateFin DATE , " +
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
                
                requeteSelectTestUserLoginSt = conn.prepareStatement(requeteSelectTestUserLogin);
                    
		    requeteInsertUserSt = conn.prepareStatement(requeteInsertUser);
		    
		    
		    
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
                mdpN = sTestBDD.next(); //entree de l'ancien mdp
                sTestBDD.nextLine(); //saute le retour a la ligne
                System.out.println("");
                System.out.println(obj.editUser(lo1,mdpO,mdpN));
                System.out.println("");
                System.out.println(obj.getUser(lo1));
                
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
                                                "date de derniere connexion = "+rs.getString(5) +"    "+"ville de reception = "+rs.getString(6);
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
         *Modifie le mote de passe d'un user
        * @param login identifiant d'un user
        * @param mdpOld mot de passe d'un user
        * @param mdpNew nouveau mot de passe d'un user
         * @return  retour - login et/ou mdp n'existe pas ou  les infos modifiee d'un user
         */
        public String editUser(String login,String mdpOld, String mdpNew)
        {
                String retour;
                System.out.println("Tentative de modification de l'user : "+login+ " avec mdp :"+mdpNew);
                
                try {
			requeteSelectTestUserLoginSt.setString(1,login);
			requeteSelectTestUserLoginSt.setString(2,mdpOld);
			
			ResultSet rs = requeteSelectTestUserLoginSt.executeQuery();
			
	        if (! rs.next()) {
	        	
	        	retour = "login ou/et mdp n'existe pas : " +login+ " Mdp :"+mdpOld;
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

}
