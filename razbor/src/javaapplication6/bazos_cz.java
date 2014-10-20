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
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fry
 */
public class bazos_cz {

    private static PrintWriter out;
    private static LinkedList<String> foto;
    private static LinkedList<String> specs;
    private static String adress = "";
    private static String price = "";
    private static String href = "";
    private static String type = ""; //1-км, 2-км, дом, участок, и т.п.
    private static String descr = "";
    private static String phone = "";
    private static String lat = "";
    private static String lon = "";

    public static void main(String[] args) throws IOException {
       
        String id = "";
        String exturl = "";
        String start = "";
        String stop = "";
        String city = ""; 
       
                
        int i = 28000;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("bazos3k.sql")), true);
        for (int n = 1; n < 946; n++) {
            Document doc = Jsoup.connect("http://reality.bazos.cz/prodam/byt-3-1/" + n + "/")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                    //   .timeout(30000)
                    .ignoreHttpErrors(true)
                    .get();
           // out.println("Страница " + (n - 1));
         //   out.println("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html");

            Elements items = doc.select("span>a[href*=/inzerat/]");

            for (Element item : items) {
                
               // out.println("Объект " + i++);
               // out.println(item.attr("href").replaceAll("//", ""));
                //  out.println("  ");
                String url = "http://reality.bazos.cz"+item.attr("href").replaceAll("//", "");
               // System.out.println("http://reality.bazos.cz"+url);
                try {
                    foto = new LinkedList<>();
                    specs = new LinkedList<>();
                    adress= "";
                    price= "";
                    href= url;
                    type= "";
                    descr= "";
                    phone= "";
                    lat="";
                    lon="";
                    
                    getAddress(url);
                    // TimeUnit.MILLISECONDS.sleep(100);
                   getPicks(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    getSpecs(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    getDescription(url);
                    out.println(" ");
                    TimeUnit.MILLISECONDS.sleep(1);

                    String sqlObject;
                  //  String tmp = razbor.GeocodingSample.getAddress(adress.split("Karte")[0]);
                    sqlObject = "insert into arc_tr2 values (" + (i) + ", \"" + adress + "\", \"" + descr.replaceAll("\"", "") + "\", \"" + specs + "\" ,\"" + phone + "\", \"" + price + "\", \"" + "Продажа квартира 3-км чехия" + type + "\",  \""+lat+"\", \""+lon+"\", \" " + href + "\", \"cz\", \"CZK\");";
                    out.println(sqlObject);
                    for (int j = 1; j < foto.size(); j++) {
                        sqlObject = " insert into arc_tr_image values (null, \"" + foto.get(j) + "\", null, null, null," + (i - 1) + ");";
                        // mysql.mysql.doInsert(sqlObject);
                        out.println(sqlObject);
                    }
                    out.println(" ");
                    i++;
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (Exception e) {
                    System.out.println("this is a problem at program " + e.getLocalizedMessage());
                }
            }
        }
    }

    public static void getPicks(String url) throws Exception {
        //картинки    
        //      out.println("запрос " + "http://www.funda.nl"+url);
        //out.println("Фотографии");
        // System.out.println("http://"+url);
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
        //  out.println(doc2.title());
        // out.println(doc2.getElementsByClass("description").toString());
        

            Elements items2 = doc2.select("img[src$=.jpg]");
           // String[] tokens = items2.toString().split(",");
           
            
            for (Element item2 : items2) {
                
              // System.out.println(item2.attr("src").replaceAll("t/", "/"));
               foto.add(item2.attr("src").replaceAll("t/", "/"));
            }

       
    }

    public static void getSpecs(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        //out.println("Спецификация");
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {
//System.out.println("specs");
            Elements items2 = doc2.select("td[colspan$=2]");
            // out.println(url);
            specs.add("kontakty "+ items2.get(0).text());
            price = items2.get(1).text().replaceAll("Kč", "");
           // System.out.println(price + " "+ specs);
        } catch (IllegalArgumentException e) {
            //   out.println("fail " + e.getLocalizedMessage());
        }

    }

    public static void getDescription(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        //  out.println("Описание");
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("td[colspan$=3]");
            //  out.println("http://"+url);
            for (Element item2 : items2) {
                descr = item2.text();
             //   System.out.println(descr);
                //out.println(item2.text());

            }
        } catch (IllegalArgumentException e) {
            //out.println("fail " + e.getLocalizedMessage());
        }

    }

    public static void getAddress(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        // out.println("Адресс");
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("a[title$=Přibližná lokalita]");
            //  out.println(url);
            for (Element item2 : items2) {
                
               // System.out.println(item2.text());
                adress = item2.text();
                lat = item2.attr("href").split("=")[5].split("&")[0].split(",")[0];
                lon = item2.attr("href").split("=")[5].split("&")[0].split(",")[1];
                //System.out.println(item2.attr("href").split("=")[5].split("&")[0]);
                //adress = item2.text();
                //out.println(item2.text());

            }
             //Elements items3 = doc2.select("dd.is24qa-kaufpreis");
            //  out.println(url);
//            for (Element item2 : items3) {
//
//                price = item2.text();
//                //out.println(item2.text());
//
//            }
        } catch (IllegalArgumentException e) {
            //   out.println("fail " + e.getLocalizedMessage());
        }

    }
}
