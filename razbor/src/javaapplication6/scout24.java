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
public class scout24 {

    private static PrintWriter out;

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        int i = 1;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("scout24.txt")), true);
        for (int n = 2; n < 3; n++) {
            Document doc = Jsoup.connect("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html")
                    .userAgent("Mozilla")
                    .get();
            out.println("Страница " + (n - 1));
            out.println("http://www.immobilienscout24.de/wohnen/berlin,berlin/mietwohnungen,seite-" + n + ".html");

            Elements items = doc.select("h3 > a[href~=/*expose*]");
            for (Element item : items) {

                out.println("Объект " + i++);
                out.println(item.attr("href").replaceAll("//",""));
                //  out.println("  ");
                String url = item.attr("href").replaceAll("//","");
                try {
                    // TimeUnit.MILLISECONDS.sleep(100);
                    getPicks(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                 //   getSpecs(url);
                    //  TimeUnit.MILLISECONDS.sleep(100);
                //   getDescription(url);
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
        Document doc2 = Jsoup.connect("http://"+url)
                .userAgent("Mozilla")
                .get();
        //  out.println(doc2.title());
        // out.println(doc2.getElementsByClass("description").toString());
        try {
            
            Element items2 = doc2.select("script").get(5);
            String[] tokens = items2.toString().split(",");
           // System.out.println("test "+items2.toString());
           
            for (int h =0; h< tokens.length; h++){
                String[] tmp = tokens[h].split("\":\"");
                // System.out.println(tokens[h]);
                if (tmp[0].equalsIgnoreCase("\"originalPictureUrl"))
                    System.out.println(tmp[1].replaceAll("\"", ""));
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
        Document doc2 = Jsoup.connect(url + "kenmerken/")
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(30000)
                .post();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("table.specs-cats tr");
            out.println(url + "kenmerken/");
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
        Document doc2 = Jsoup.connect(url + "omschrijving/")
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(30000)
                .post();
           //   out.println(doc2.title());

        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("div.description-full");
            out.println(url + "omschrijving/");
            for (Element item2 : items2) {

                out.println(item2.text());

            }
        } catch (IllegalArgumentException e) {
            out.println("fail " + e.getLocalizedMessage());
        }

    }

}
