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
public class gratka_pl {

    private static PrintWriter out;
    private static LinkedList<String> foto;
    private static LinkedList<String> specs;
    private static String adress;
    private static String price;
    private static String href;
    private static String type; //1-км, 2-км, дом, участок, и т.п.
    private static String descr;
    private static String phone;
    private static String lat = "";
    private static String lon = "";

    public static void main(String[] args
    ) throws IOException, InterruptedException, java.net.SocketTimeoutException {
            int i = 26672;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("sell_poland.sql")), true);
        for (int n = 100; n < 200; n++) {
            
            Document doc = Jsoup.connect("http://dom.gratka.pl/mieszkania-sprzedam/lista/,,20,dm_0,"+n+",li,sr,s.html")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                    .get();
            // out.println("Страница " + (n - 1));
            // out.println("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html");
            System.out.println("http://dom.gratka.pl/mieszkania-sprzedam/lista/,,20,dm_0,"+n+",li,sr,s.html");
            Elements items = doc.select("a[href~=/*tresc/d*]");
            
            for (Element item : items) {
            
               // out.println("Объект " + i++);
                //  out.println(item.attr("href").replaceAll("//",""));
                //  out.println("  ");
                String url = "http://dom.gratka.pl" + item.attr("href").replaceAll("//", "");
                href= url;
                // System.out.println(url);
                try {
                    lat = "NaN";
                    lon = "Nan";
                    // TimeUnit.MILLISECONDS.sleep(100);
                    getPicks(url, i);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                        getSpecs(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    //  getDescription(url);
                  //  out.println(" ");
                    //      TimeUnit.SECONDS.sleep(1);
                         TimeUnit.MILLISECONDS.sleep(200);

                    String sqlObject;
                  //  String tmp = razbor.GeocodingSample.getAddress(adress.split("Karte")[0]);
                    sqlObject = "insert into arc_tr2 values (" + (i) + ", \"" + adress + "\", \"" + descr.replaceAll("\"", "").replaceAll("\'", "\\\'") + "\", \"" + specs + "\" ,\"" + phone + "\", \"" + price + "\", \"" + "Продажа квартира Польша" + type + "\",  \""+lat+"\", \""+lon+"\", \" " + href + "\", \"pl\", \"PLN\");";
                    out.println(sqlObject);
                    for (int j = 1; j < foto.size(); j++) {
                        sqlObject = " insert into arc_tr_image values (null, \"" + foto.get(j) + "\", null, null, null," + (i) + ");";
                        // mysql.mysql.doInsert(sqlObject);
                        out.println(sqlObject);
                    }
                    out.println(" ");
                    i++;
                    
                } catch (Exception e) {
                    System.out.println("this is a problem at program " + e.getMessage());
                }
            }
        }
        
        System.exit(0);
    }

    public static void getPicks(String url, int i) throws IOException  {
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
            
            Elements items2 = doc2.select("a[href^=http://img.nc.grtech.pl]");

            // System.out.println("test "+items2.toString());
            for (Element item2 : items2) {
                //System.out.println(item2.attr("href"));
                //out.println(item2.attr("href"));
                
                foto.add(item2.attr("href"));

            }
          
         
            
            
            
        } catch (IllegalArgumentException e) {
            // out.println("fail " + e.getLocalizedMessage());
        }
    }

    public static void getSpecs(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
       // out.println("Спецификация");
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
           //   out.println(doc2.title());
        specs = new LinkedList<>();
        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("div.mieszkanie>ul>li");
            //out.println(url);
            //System.out.println("!!!!!!!!");
            for (Element item2 : items2) {
               // System.out.println(item2.text());
                
                specs.add(item2.text()+";");

            }
            
            items2 = doc2.select("div.cenaGlowna>p");
            //out.println(url);
           // System.out.println("!!!!!!!!");
            for (Element item2 : items2) {
                price =item2.text();
               
                //out.println(item2.text());

            }
            items2 = doc2.select("div[id$=opis-dodatkowy]>div>p");
            //out.println(url);
           // System.out.println("!!!!!!!!");
            for (Element item2 : items2) {
                descr = item2.html();
               
                //out.println(item2.text());

            }
            lat = doc2.select("span.latitude").text();
            lon = doc2.select("span.longitude").text();
            
            
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
