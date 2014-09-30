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
public class scout24 {

    private static PrintWriter out;
    private static LinkedList<String> foto;
    private static LinkedList<String> specs;
    private static String adress = "";
    private static String price = "";
    private static String href = "";
    private static String type = ""; //1-км, 2-км, дом, участок, и т.п.
    private static String descr = "";
    private static String phone = "";

    public static void main(String[] args) throws IOException {
       
        String id = args[0];
        String exturl = args[1];
        String start = args[2];
        String stop = args[3];
        String city = args[4];  
        int i = 19537;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("scout24_"+city+".sql")), true);
        for (int n = 1; n < 500; n++) {
            Document doc = Jsoup.connect("http://www.immobilienscout24.de/wohnen/bayern/eigentumswohnungen,seite-" + n + ".html")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2")
                    //   .timeout(30000)
                    .ignoreHttpErrors(true)
                    .get();
           // out.println("Страница " + (n - 1));
         //   out.println("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html");

            Elements items = doc.select("h3 > a[href~=/*expose*]");

            for (Element item : items) {

               // out.println("Объект " + i++);
               // out.println(item.attr("href").replaceAll("//", ""));
                //  out.println("  ");
                String url = item.attr("href").replaceAll("//", "");
                try {
                    foto = new LinkedList<>();
                    specs = new LinkedList<>();
                    adress= "";
                    price= "";
                    href= "http://"+ url;
                    type= "";
                    descr= "";
                    phone= "";
                    
                    getAddress(url);
                    // TimeUnit.MILLISECONDS.sleep(100);
                    getPicks(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    getSpecs(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    getDescription(url);
                    out.println(" ");
                    TimeUnit.MILLISECONDS.sleep(100);

                    String sqlObject;
                  //  String tmp = razbor.GeocodingSample.getAddress(adress.split("Karte")[0]);
                    sqlObject = "insert into tr values (" + (i - 1) + ", \"" + adress + "\", \"" + descr + "\", \"" + specs + "\" ,\"" + phone + "\", \"" + price + "\", \"" + "Продажа квартира бавария" + type + "\",  \"NaN\", \"NaN\", \" " + href + "\");";
                    out.println(sqlObject);
                    for (int j = 0; j < foto.size(); j++) {
                        sqlObject = " insert into tr_image values (null, \"" + foto.get(j) + "\", null, null, null," + (i - 1) + ");";
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
        Document doc2 = Jsoup.connect("http://" + url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
        //  out.println(doc2.title());
        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Element items2 = doc2.select("script").get(5);
            String[] tokens = items2.toString().split(",");
            // System.out.println("test "+items2.toString());

            for (int h = 0; h < tokens.length; h++) {
                String[] tmp = tokens[h].split("\":\"");
                // System.out.println(tokens[h]);
                if (tmp[0].equalsIgnoreCase("\"originalPictureUrl")) {
                    foto.add(tmp[1].replaceAll("\"", ""));
                    //out.println(tmp[1].replaceAll("\"", ""));
                }
            }
//            for (Element item2 : items2) {
//                System.out.println(item2.text());
//                out.println(item2.attr("src").replaceAll("klein", "grotere"));
//            }

        } catch (IllegalArgumentException e) {
            // out.println("fail " + e.getLocalizedMessage());
        }
    }

    public static void getSpecs(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        //out.println("Спецификация");
        Document doc2 = Jsoup.connect("http://" + url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("div.is24-ex-details");
            // out.println(url);
            for (Element item2 : items2) {

                specs.add(item2.text());
                //out.println(item2.text());

            }
        } catch (IllegalArgumentException e) {
            //   out.println("fail " + e.getLocalizedMessage());
        }

    }

    public static void getDescription(String url) throws Exception {
        //картинки    
        //     out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
        //  out.println("Описание");
        Document doc2 = Jsoup.connect("http://" + url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("div.is24-text");
            //  out.println("http://"+url);
            for (Element item2 : items2) {
                descr = item2.text();
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
        Document doc2 = Jsoup.connect("http://" + url)
                .userAgent("Mozilla")
                .timeout(2 * 1000)
                .get();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("div[data-qa$=is24-expose-address]");
            //  out.println(url);
            for (Element item2 : items2) {

                adress = item2.text();
                //out.println(item2.text());

            }
             Elements items3 = doc2.select("dd.is24qa-kaufpreis");
            //  out.println(url);
            for (Element item2 : items3) {

                price = item2.text();
                //out.println(item2.text());

            }
        } catch (IllegalArgumentException e) {
            //   out.println("fail " + e.getLocalizedMessage());
        }

    }
}
