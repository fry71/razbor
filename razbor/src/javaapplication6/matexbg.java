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
import java.util.concurrent.TimeUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fry
 */
public class matexbg {

    private static PrintWriter out;

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        int i = 1;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("matex.txt")), true);
        for (int n = 2; n < 3; n++) {
            Document doc = Jsoup.connect("http://www.matex.bg/search.html?s_type=0&s_price_from=&s_price_to=&s_area_from=&s_area_to=&s_location=0&s_ref_num=&s_searchtype=1")
                    .userAgent("Mozilla")
                    .get();
           // out.println("Страница " + (n - 1));
           // out.println("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html");

            Elements items = doc.select("td > a[href~=/*matexoffer*]");
            for (Element item : items) {

                out.println("Объект " + i++);
              //  out.println(item.attr("href").replaceAll("//",""));
                //  out.println("  ");
                String url = "http://www.matex.bg/" +item.attr("href").replaceAll("//","");
               // System.out.println(url);
                try {
                    // TimeUnit.MILLISECONDS.sleep(100);
                    getPicks(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                    getSpecs(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                   getDescription(url);
                    out.println(" ");
              //      TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    System.out.println("this is a problem at program " + e.getLocalizedMessage());
                }
            }
        }
    }

    public static void getPicks(String url) throws Exception {
        //картинки    
        //      out.println("запрос " + "http://www.funda.nl"+url);
        out.println("Фотографии");
       // System.out.println("http://"+url);
        Document doc2 = Jsoup.connect(url)
                .userAgent("Mozilla")
                .get();
        //  out.println(doc2.title());
        // out.println(doc2.getElementsByClass("description").toString());
        try {
            
            Elements items2 = doc2.select("img[src$=_s.JPG]");
            
           // System.out.println("test "+items2.toString());
           for (Element item2 : items2) {
            
                out.println("http://www.matex.bg/"+item2.attr("src").replaceAll("_s", ""));

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

