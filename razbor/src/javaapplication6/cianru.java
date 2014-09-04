/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fry
 */
public class cianru {

    private static PrintWriter out;
    private static LinkedList<String> foto;
    private static LinkedList<String> specs;
    private static String adress;
    private static String price;
    private static String href;
    private static String type; //1-км, 2-км, дом, участок, и т.п.
    private static String descr;
    private static String phone;

    public static void main(String[] args
    ) throws IOException, InterruptedException, Exception {
        int i = 11421;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("sell_cian.txt")), true);
        for (int n = 440; n < 1000; n++) {
            Document doc = Jsoup.connect("http://www.cian.ru/cat.php?deal_type=2&obl_id=1&city[0]=1&room1=1&room2=1&room3=1&room4=1&room5=1&room6=1&p=" + n)
                    .userAgent("Mozilla")
                    .get();
            // out.println("Страница " + (n - 1));
            // out.println("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html");
            
            Elements items = doc.select("a[href~=/*sale/flat/\\d*]");
            for (Element item : items) {
            i++;
               // out.println("Объект " + i++);
                //  out.println(item.attr("href").replaceAll("//",""));
                //  out.println("  ");
                String url = "http://www.cian.ru" + item.attr("href").replaceAll("//", "");
                // System.out.println(url);
                try {

                    // TimeUnit.MILLISECONDS.sleep(100);
                    getPicks(url, i);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    //    getSpecs(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    //  getDescription(url);
                    out.println(" ");
                    //      TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    System.out.println("this is a problem at program " + e.getLocalizedMessage());
                }
            }
        }
        
        System.exit(0);
    }

    public static void getPicks(String url, int i) throws Exception {
        //картинки    
        //      out.println("запрос " + "http://www.funda.nl"+url);
       // out.println("Фотографии");
        foto = new LinkedList<>();
        // System.out.println("http://"+url);
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
        //  out.println(doc2.title());
        // out.println(doc2.getElementsByClass("description").toString());
        try {
            href = "";
            href = url;
            Elements items2 = doc2.select("a[href$=.jpg]");

            // System.out.println("test "+items2.toString());
            for (Element item2 : items2) {
                //System.out.println(item2.attr("href"));
                //out.println(item2.attr("href"));
                foto.add(item2.attr("href"));

            }
            adress = "";
            items2 = doc2.select("h1.object_descr_addr");
            //out.println("Адресс");
            //out.println(items2.text());
            adress = items2.text();

            price = "";
            items2 = doc2.select("div.object_descr_price ");
            //out.println("Цена");
            // out.println(items2.text());
            price = items2.text();
            type = "";
            items2 = doc2.select("div.object_descr_title ");
           // out.println("Спецификация");
            // out.println(items2.text());
            type = items2.text();
            items2 = doc2.select("table.object_descr_props > tbody > tr ");
            specs = new LinkedList<>();
            for (Element item2 : items2) {
                //System.out.println(item2.attr("href"));
                // out.println(item2.text());
                specs.add(item2.text());

            }
            descr = "";
            items2 = doc2.select("div.object_descr_text ");
            //out.println("Описание");
            //out.println(items2.text());
            descr = items2.text();
            phone = "";
            items2 = doc2.select("strong.object_descr_phone_orig ");
            
            //out.println("Телефон");
            //out.println(items2.text());
            phone = items2.text();
            //out.println();
            
            //mysql.mysql.getConnection();
            try {
                Pattern p = Pattern.compile("-?\\d+");
                Matcher m = p.matcher(price);
                price="";
                while (m.find()) {
                   price+=m.group();
                    
                    
                }
              //  System.out.println(price);
               // System.out.println(BigInteger.(price));
            } catch (Exception e) {
                System.out.println(e);
            }

            String sqlObject;
            String tmp = razbor.GeocodingSample.getAddress(adress);
           // System.out.println(razbor.GeocodingSample.getAddress("Москва, Дубининская улица, 20с1"));
            sqlObject = "insert into tr values ("+(i-1)+", \""+adress+"\", \""+descr+"\", \""+specs+"\" ,\""+phone+"\", \""+price+"\", \""+"Продажа "+type+"\",  \""+tmp.split(",")[0]+"\", \""+tmp.split(",")[1]+"\", \" "+href+"\");";
            
            
//           mysql.mysql.doInsert(sqlObject);
//           sqlObject = "insert into galery values ("+(i-1)+","+(i-1)+")";
//           
        //   mysql.mysql.doInsert(sqlObject);
            out.println(sqlObject);
          // sqlObject="";
            for (int j = 0; j < foto.size(); j++) {
                sqlObject =" insert into image values (null, \""+foto.get(j)+"\", null, null, null,"+(i-1)+");";
               // mysql.mysql.doInsert(sqlObject);
                out.println(sqlObject);
            }
            
          // mysql.mysql.closeConection();
            
            
            
        } catch (IllegalArgumentException e) {
            // out.println("fail " + e.getLocalizedMessage());
        }
    }

    public static void getSpecs(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        out.println("Спецификация");
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("table[width$=210] > tbody > tr");
            out.println(url);
            for (Element item2 : items2) {

                out.println(item2.text());

            }
        } catch (IllegalArgumentException e) {
            out.println("fail " + e.getLocalizedMessage());
        }

    }

    public static void getDescription(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        out.println("Описание");
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(30000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("td.InfosBrif");
            out.println(url + "omschrijving/");
            for (Element item2 : items2) {

                out.println(item2.text());

            }
        } catch (IllegalArgumentException e) {
            out.println("fail " + e.getLocalizedMessage());
        }

    }

}
