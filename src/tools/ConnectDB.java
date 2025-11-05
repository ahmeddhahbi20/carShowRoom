/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dhahb
 */
public class ConnectDB {
    public static Connection getCon() throws SQLException, ClassNotFoundException {
    try{
        Class.forName("com.mysql.jdbc.Driver");   
        Connection connect =DriverManager.getConnection("jdbc:mysql://localhost/location_voiture","root","ahmedd");
            return connect;
            
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getcon() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
