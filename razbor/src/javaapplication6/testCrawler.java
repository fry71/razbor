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
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author fry
 */
public class testCrawler {

    private static PrintWriter out;

    public static void main(String[] args) throws IOException, InterruptedException, Exception {
        Document doc = Jsoup.connect("http://www.funda.nl/europe/heel-europa/1-10-kamers/")
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(30000)
                .post();
        int i = 1;
        out = new PrintWriter(new OutputStreamWriter(new FileOutputStream("test")), true);
        Elements items = doc.select("a[href~=/*huis*]");
        for (Element item : items) {
            out.println("Объект " + i++);
            //  out.println("  ");
            String url = "http://www.funda.nl" + item.attr("href");
          
            // TimeUnit.MILLISECONDS.sleep(100);
            getPicks(url);
            //  TimeUnit.MILLISECONDS.sleep(100);
            getSpecs(url);
            //  TimeUnit.MILLISECONDS.sleep(100);
            getDescription(url);
            out.println(" ");
        }

    }

    public static void getPicks(String url) throws Exception {
        //картинки    
       //      out.println("запрос " + "http://www.funda.nl"+url);
        out.println("Фотографии");
        Document doc2 = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(30000)
                .post();
              //  out.println(doc2.title());
        // out.println(doc2.getElementsByClass("description").toString());
        try {

            Elements items2 = doc2.select("img[src$=.jpg]");

            for (Element item2 : items2) {

                out.println(item2.attr("src").replaceAll("klein", "grotere"));
            }
        } catch (IllegalArgumentException e) {
            out.println("fail " + e.getLocalizedMessage());
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
