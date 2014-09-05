/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

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

    public static void getConnection() {

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeConection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(mysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void doInsert(String sql) {
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

    /**
     *
     * @param data
     */
    public static void doGoogleMaps(LinkedList<data> data, int id) throws SQLException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {
      
            getConnection();
            Statement stmt = null;
            stmt = conn.createStatement();
            Statement stmt2;
            stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select id, address from tr where id >  " + id);
            while (rs.next()) {
                
                String addr = razbor.GeocodingSample.getAddress(rs.getString("address"));
                //System.out.println(addr);
                
                System.out.println("update tr set lat = "+addr.split(";")[0].trim()+", lon = "+addr.split(";")[1].trim()+" where id = "+rs.getInt("id")+";");
                int test = stmt2.executeUpdate("update tr set lat = \""+addr.split(";")[0].trim()+"\", lon = \""+addr.split(";")[1].trim()+"\" where id = "+rs.getInt("id")+";");
                System.out.println("результат запроса :"+ test);
                TimeUnit.MILLISECONDS.sleep(100);
              //  data.add(new data(rs.getInt("id"), rs.getString("address")));
            }
            closeConection();

       

    }

}
