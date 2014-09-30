/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;
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
    public static void doGoogleMaps() throws SQLException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {

        getConnection();
        Statement stmt = null;
        stmt = conn.createStatement();
        Statement stmt2;
        stmt2 = conn.createStatement();
        int id;
        ResultSet rs = stmt.executeQuery("SELECT MAX( id ) id FROM tr");
        rs.last();
        id = rs.getInt(0);
        System.out.println(id);
        System.exit(0);
        rs = stmt.executeQuery("select id, address from tr where id >  " + id);
        while (rs.next()) {

            String addr = razbor.GeocodingSample.getAddress(rs.getString("address"));
            //System.out.println(addr);

            System.out.println("update tr set lat = " + addr.split(";")[0].trim() + ", lon = " + addr.split(";")[1].trim() + " where id = " + rs.getInt("id") + ";");
            int test = stmt2.executeUpdate("update tr set lat = \"" + addr.split(";")[0].trim() + "\", lon = \"" + addr.split(";")[1].trim() + "\" where id = " + rs.getInt("id") + ";");
            System.out.println("результат запроса :" + test);
            TimeUnit.MILLISECONDS.sleep(100);
            //  data.add(new data(rs.getInt("id"), rs.getString("address")));
        }
        closeConection();

    }

    public static void parseSpecs() throws SQLException {
        mysql.getConnection();

        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id, specs from arc_tr where href like '%cian.ru%' and id < 9000 and id > 5999 order by id");
        Statement stmt2;
        stmt2 = conn.createStatement();
        String[] tmpStr;
        String[] tmpStr2;
        Pattern p = Pattern.compile("^[А-Яа-я:\\s]{3,20}$");
       LinkedList<String> paramNames = new LinkedList<String>() {};
        LinkedList<String> paramValue = new LinkedList<String>();
        LinkedList<Integer> tr_id = new LinkedList<Integer>();
        while (rs.next()) {
            tmpStr = rs.getString("specs").split(";");

            for (int i = 0; i < tmpStr.length; i++) {
                Matcher m = p.matcher(tmpStr[i].split(": ")[0]);

               // System.out.println(tmpStr[i].split(": ")[0] + " - " +m.matches());
               // System.out.println(tmpStr[i]);
                if (m.matches() &&  2 == tmpStr[i].split(": ").length) {
                    paramNames.add(tmpStr[i].split(": ")[0]);
                    paramValue.add(tmpStr[i].split(": ")[1]);
                    tr_id.add(rs.getInt("id"));
                }

            }

           // int test = stmt2.executeUpdate("update arc_tr set specs = \"" + rs.getString("specs").replaceAll(", ", "; ") + "\" where id = " + rs.getInt("id") + ";");
            //System.out.println("результат запроса : " + test + "id = " + rs.getInt("id"));
            // System.out.println(rs.getString("specs").replaceAll(", ", "; "));
        }
        /*  paramNames.remove("[Общая информация");
        paramNames.remove("[Общая информация:");*/
        paramNames.remove("деревянный");
        paramNames.remove("кирпичный");
        paramNames.remove("блочный");
        paramNames.remove("монолитный");
        
        for (int i = 0; i < paramNames.size(); i++) {
            String spec_name_id = null;
           // System.out.println("{"+paramNames.get(i)+"]");
            switch (paramNames.get(i).trim()) {
                case "Жилая площадь":
                    spec_name_id = "1";
                    break;
                case "Тип продажи":
                    spec_name_id = "2";
                    break;
                case "Общая площадь":
                    spec_name_id = "3";
                    break;
                case "Санузел":
                    spec_name_id = "4";
                    break;
                case "Вид из окна":
                    spec_name_id = "5";
                    break;
                case "Площадь кухни":
                    spec_name_id = "6";
                    break;
                case "Площадь комнат":
                    spec_name_id = "7";
                    break;
                case "Этаж":
                    spec_name_id = "8";
                    break;
                case "Лифт":
                    spec_name_id = "9";
                    break;
                case "Балкон":
                    spec_name_id = "10";
                    break;
                case "Тип дома":
                    spec_name_id = "11";
                    break;
        }
            
            System.out.println("insert into arc_tr_specs_value values (null,\"" + paramValue.get(i).replace("]", "") + "\", \"" + tr_id.get(i) + "\", \"" + spec_name_id + "\");");

         //   System.out.println("insert into arc_tr_specs_values values (null,\""+ paramName +"\", \"\")");
        }
        System.out.println(paramNames.toString());
        mysql.closeConection();

    }

    public static void getPics(int start, int stop) throws SQLException, MalformedURLException, IOException {
        mysql.getConnection();
        URL url = null;
        File file = null;
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select image_id, galery_id, image_big from arc_tr_image where galery_id > " + start + " and galery_id < " + stop);
        while (rs.next()) {
            try {

                // rs.getString("galery_id, image_big");
                url = new URL(rs.getString("image_big"));
                BufferedImage image = null;
                file = new File("img/" + rs.getString("galery_id") + "/" + "/" + rs.getString("image_id") + ".png");
                image = ImageIO.read(url);

                file.mkdirs();
                ImageIO.write(image, "png", file);
            } catch (Exception e) {
                System.out.println(e);
                System.out.println(url);

                System.out.println(file.getPath());
            }
        }

    }

}
