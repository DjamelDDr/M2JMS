/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projectjmsmodandr.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Hamza D
 */
public class DataJDBC {
    
    private Connection conn;
	
    private static final String requeteSelectLogin = "select login from USER where login = ?";
    private static final String requeteSelectTestLogin = "select login from USER where login = ? and mdp = ?";
    private static final String requeteInsert = "insert into USER values (?, ?, ?, ?, ?, ? )";
    private static final String requeteUpdateEditAbo = "update USER set mdp = ? where login = ?";
    private static final String requeteGetUser = "select login, mdp, nom, prenom, dateDerniereConnexion, villeReception from USER where login = ?";
    private static final String requeteDelete = "delete from USER where login = ? and mdp = ?";
    private static final String requeteInsertEmprunter = "insert into EMPRUNTER values (?, ?, ? )";

    private PreparedStatement requeteSelectLoginSt = null;
    private PreparedStatement requeteSelectTestLoginSt = null;
    private PreparedStatement requeteInsertSt = null;
    private PreparedStatement requeteDeleteSt = null;
    private PreparedStatement requeteUpdateEditAboSt = null;
    private PreparedStatement requeteUpdateRetraitSt = null;
    private PreparedStatement requeteGetUserSt = null;

    
    public DataJDBC(String nomBD) {
        
        try {
                // récupération du driver
		    Class.forName("org.h2.Driver");
                    
		    // création d'une connexion
		    conn = DriverManager.getConnection("jdbc:h2:"+nomBD+";IGNORECASE=TRUE", "sa", "");
                    
	        // On regarde si la table existe deja
	        try {
	        	// construction du prepared statement
	        	requeteSelectLoginSt = conn.prepareStatement(requeteSelectLogin);
	        } catch(Exception e) {
                    
	        	// sinon on l'a cree
	        	Statement s = conn.createStatement();
	        	
         	s.execute("create table USER  ( " +
	        			"login VARCHAR( 256 ) , " +
	        			"mdp VARCHAR( 256 ) , " +
                                        "nom VARCHAR( 256 ) , " +
                                        "prenom VARCHAR( 256 ) , " +
                                        "dateDerniereConnexion DATE , " +
	        			"villeReception VARCHAR( 256 ) ," +
	        			"CONSTRAINT pk_login PRIMARY KEY(login ) ," + 
	        			"CONSTRAINT nn_type_log_client CHECK(login IS NOT NULL) )"
	        		);
         	

        	//on ajoute des entrees des USER
        	s.executeUpdate("insert into USER values ('amz','1234','danpollo','hamza','2015-11-09','Paris')");
        	s.executeUpdate("insert into USER values ('kabal','1805','moco','kevin','2015-11-01','Toulouse')");
        	s.executeUpdate("insert into USER values ('djadja','0000','drif','djamel','2015-02-14','Toulouse')");
	        	
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
	        	requeteSelectLoginSt = conn.prepareStatement(requeteSelectLogin);
	        }
	        // construction des autres prepared statement
	        requeteDeleteSt = conn.prepareStatement(requeteDelete);
		    requeteInsertSt = conn.prepareStatement(requeteInsert);
		    requeteSelectTestLoginSt = conn.prepareStatement(requeteSelectTestLogin);
		    requeteUpdateEditAboSt = conn.prepareStatement(requeteUpdateEditAbo);
		    requeteGetUserSt = conn.prepareStatement(requeteGetUser);
		  //  requeteInsertEmprunterSt = conn.prepareStatement(requeteInsertEmprunter);
		  //  requeteSelectTestEmprunterSt = conn.prepareStatement(requeteSelectTestEmprunter);
		   // requeteUpdateRetraitSt = conn.prepareStatement(requeteUpdateRetrait);
		    
		    
		} catch(Exception e) {
			// il y a eu une erreur
			e.printStackTrace();
		}
    }
    
    	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		//LocateRegistry.createRegistry(1099);
		DataJDBC obj = new DataJDBC("maBddTwitter");
		//Naming.rebind("Velo2WinD", obj);
                
                //test get User
                System.out.println(obj.GetUser("djadja"));
                
                

	}
        /**
        * @param login
        **/
	public String GetUser(String login)
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

}
