/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fry
 */
public class mysql {

    // JDBC driver name and database URL

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://85.10.209.11:3306/redb_eu?useUnicode=true&characterEncoding=utf-8";
    //  Database credentials
    static final String USER = "fry";
    static final String PASS = "templar22";
    static public Connection conn;
    
    public static void getConnection(){
        
        try{
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void closeConection(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void doInsert(String sql){
        try {
            Statement stmt = null;
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        //    conn.commit();
        } catch (SQLException ex) {
            System.out.println(sql);
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void doGoogleMaps(){
        try {
            Statement stmt = null;
            stmt = conn.createStatement();
            stmt.executeQuery(USER);
            
        } catch (SQLException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
