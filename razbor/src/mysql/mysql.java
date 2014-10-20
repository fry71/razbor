/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import com.gtranslate.Language;
import com.gtranslate.Translator;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.HashSet;
import java.util.Iterator;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
        ResultSet rs = stmt.executeQuery("SELECT MAX( id ) id FROM arc_tr");
        rs.last();
        id = rs.getInt(1);
        System.out.println(id);

        rs = stmt.executeQuery("select id, address from arc_tr2 where lat like 'NaN'  ");
        while (rs.next()) {

            String addr = razbor.GeocodingSample.getAddress(rs.getString("address"));
            //System.out.println(addr);

            System.out.println("update arc_tr2 set lat = " + addr.split(";")[0].trim() + ", lon = " + addr.split(";")[1].trim() + " where id = " + rs.getInt("id") + ";");
            int test = stmt2.executeUpdate("update arc_tr2 set lat = \"" + addr.split(";")[0].trim() + "\", lon = \"" + addr.split(";")[1].trim() + "\" where id = " + rs.getInt("id") + ";");
            System.out.println("результат запроса :" + test);
            TimeUnit.MILLISECONDS.sleep(100);
            //  data.add(new data(rs.getInt("id"), rs.getString("address")));
        }
        closeConection();

    }

    public static void getTowns() throws SQLException {
        mysql.getConnection();

        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT `address` FROM `arc_tr` where  type  like \"%funda%\" ");
        HashSet towns = new HashSet();
        while (rs.next()) {
            String tmp, item = "";
            tmp = rs.getString(1).split(",")[0];
            for (int i = 0; i < tmp.split(" ").length; i++) {
                try {
                    Integer.parseInt(tmp.split(" ")[i]);
                } catch (Exception e) {
                    item += tmp.split(" ")[i] + " ";
                }
            }
            towns.add(item);

        }
        //System.out.println(towns);
        for (Iterator iterator = towns.iterator(); iterator.hasNext();) {
            Object next = iterator.next();
            System.out.println(next);

        }
        mysql.closeConection();
    }
    private static PrintWriter out;

    public static void parseSpecs() throws SQLException, FileNotFoundException {
        mysql.getConnection();

        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id, specs from arc_tr2 where href like '%cian.ru%' and id > 15650  AND id < 16000 order by id");
        Statement stmt2;
        stmt2 = conn.createStatement();
        String[] tmpStr;
        String[] tmpStr2;
        Pattern p = Pattern.compile("^[А-Яа-я:\\s]{3,20}$");
        LinkedList<String> paramNames = new LinkedList<String>() {
        };
        LinkedList<String> paramValue = new LinkedList<String>();
        LinkedList<Integer> tr_id = new LinkedList<Integer>();
        while (rs.next()) {
            tmpStr = rs.getString("specs").split(";");

            for (int i = 0; i < tmpStr.length; i++) {
                Matcher m = p.matcher(tmpStr[i].split(": ")[0]);

                // System.out.println(tmpStr[i].split(": ")[0] + " - " +m.matches());
                // System.out.println(tmpStr[i]);
                if (m.matches() && 2 == tmpStr[i].split(": ").length) {
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

    public static void parcPrice() throws SQLException {
        mysql.getConnection();
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id, price from arc_tr2 where href like '%scout%' order by id ");
        while (rs.next()) {
            //System.out.println(rs.getString(1)+" "+rs.getString(2).substring(0, rs.getString(2).length()-3)+"."+rs.getString(2).substring(rs.getString(2).length()-3));
            System.out.println("update arc_tr2 set price = \"" + rs.getString(2).substring(0, rs.getString(2).length() - 3) + "." + rs.getString(2).substring(rs.getString(2).length() - 3) + "\" where id =" + rs.getString(1) + ";");
        }

        mysql.closeConection();
    }

    public static void parseSpecsScout() throws SQLException, IOException, InterruptedException {
        mysql.getConnection();
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("scout24_specs.sql")), true);
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id, specs, href, type from arc_tr2 where href like '%scout%' and id > 15015 order by id limit 2000");
        Statement stmt2;
        stmt2 = conn.createStatement();
        String[] tmpStr;
        String[] tmpStr2;
        Pattern p = Pattern.compile("^[А-Яа-я:\\s]{3,20}$");
        LinkedList<String> paramNames = new LinkedList<String>() {
        };
        LinkedList<String> paramValue = new LinkedList<String>();
        LinkedList<Integer> tr_id = new LinkedList<Integer>();
        while (rs.next()) {
            //System.out.println(rs.getString("href") + " "+ rs.getString("specs"));

            Document doc2 = Jsoup.connect(rs.getString("href").trim())
                    .userAgent("Mozilla")
                    .timeout(2 * 1000)
                    .get();
           //   out.println(doc2.title());

            // out.println(doc2.getElementsByClass("description").toString());
            try {

                Elements items2 = doc2.select("dl");
                // System.out.println(" ");
                String specs = "";
                for (Element item2 : items2) {
                    if (!item2.text().equalsIgnoreCase("Monatliche Rate berechnen")) {
                        if (item2.text().split(":").length > 1) {
                            specs += item2.text() + ";";
                        } else {
                            specs += item2.text() + " true;";
                        }
                    }
//                try{
//                   
//            String spec_name_id = null;
//                switch (item2.text().split(":")[0]) {
//            case "Wohnfläche ca.":
//                spec_name_id = "1";
//                break;
//            case "Тип продажи":
//                spec_name_id = "2";
//                break;
//            case "Общая площадь":
//                spec_name_id = "3";
//                break;
//            case "Санузел":
//                spec_name_id = "4";
//                break;
//            case "Вид из окна":
//                spec_name_id = "5";
//                break;
//            case "Площадь кухни":
//                spec_name_id = "6";
//                break;
//            case "Площадь комнат":
//                spec_name_id = "7";
//                break;
//            case "Etage":
//                spec_name_id = "8";
//                break;
//            case "Лифт":
//                spec_name_id = "9";
//                break;
//            case "Балкон":
//                spec_name_id = "10";
//                break;
//            case "Тип дома":
//                spec_name_id = "11";
//                break;
//            case "Zimmer":
//                spec_name_id = "12";
//                break;        
//        }
//              //  System.out.print((item2.text().split(":")[0])+ "   ");
//              //  System.out.println((item2.text().split(":")[1]));
//                   if(spec_name_id != null){
//                       
//                      out.println("insert into arc_tr_specs_value values (null,\"" + item2.text().split(":")[1] + "\", \"" + rs.getString("id") + "\", \"" + spec_name_id + "\");");
//                   }
//
//                }
//                catch(ArrayIndexOutOfBoundsException e){
//                    //System.out.println("НЕ ТО !!!!!!!!!!!!!!!");
//                }

                }
                //System.out.println(specs);
                if (specs.length() > 10) {
                    out.println("update arc_tr2 set specs = \"" + specs + "\", 'type'= \"" + rs.getString("type") + " updated\" where id = " + rs.getString("id") + ";");
                }
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (IllegalArgumentException e) {
                //   out.println("fail " + e.getLocalizedMessage());
            }
        }

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

    public static void getTranslate(int start, int stop) throws SQLException, ArrayIndexOutOfBoundsException {
        mysql.getConnection();
        URL url = null;
        File file = null;
        Statement stmt = null;
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select id, address, description, specs, type, lang  from arc_tr2 where id > " + start + " and id < " + stop+" and lang =\"ru\"");
        while (rs.next()) {
            Translator translate = Translator.getInstance();
            String text = rs.getString("specs").replaceAll("[+]", "/").replaceAll("новостройка;", "новостройка ").replace("вторичка;", "вторичка");
            //System.out.println(rs.getString("id"));
            /// System.out.println(text);
            //text = translate.translate(text.replaceAll(".", " "), Language.RUSSIAN, Language.ENGLISH);
            String newText = "";
            try{
            for (int i = 1; i < text.split(";").length; i++) {
               
                String name = translate.translate(text.split(";")[i].split(":")[0], Language.RUSSIAN, Language.ENGLISH);
                String value = null;
                if(!name.equalsIgnoreCase("floor")){
                value = translate.translate(text.split(";")[i].split(":")[1], Language.RUSSIAN, Language.ENGLISH);
                }else {
                    value = text.split(";")[i].split(":")[1];
                }
                newText += name+" : "+  value.replaceAll("]", "").replaceAll("sqm", " m²")+ ";";
            }}
            catch (IndexOutOfBoundsException e){
                System.out.println("delete from arc_tr2_en where id = " + rs.getString("id") + ";");
            }
            //System.out.println(text);
            System.out.println("update arc_tr2_en set specs = \'" + newText + "\' where id = " + rs.getString("id") + ";");

        }
    }

}
