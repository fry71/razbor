/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication6;

import java.io.IOException;
import java.util.LinkedList;
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
    
    public static void main(String[] args) throws IOException {
     Document doc = Jsoup.connect("http://www.funda.nl/europe/heel-europa/1-10-kamers/")
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
     int i=0;
            Elements items = doc.select("a[href~=/*huis*]");
            for (Element item : items) {
                System.out.println(i++);
          //картинки
                
                
           //     System.out.println("запрос " + "http://www.funda.nl/"+item.attr("href"));
           Document doc2 = Jsoup.connect("http://www.funda.nl/"+item.attr("href") )
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
              //  System.out.println(doc2.title());
               // System.out.println(doc2.getElementsByClass("description").toString());
                try{
               
            Elements items2 = doc2.select("img[src$=.jpg]");
            
            for (Element item2 : items2) {
                 
                 System.out.println( item2.attr("src").replaceAll("klein", "grotere") );
            }
                }catch( IllegalArgumentException e){System.out.println("fail " + e.getLocalizedMessage());}
        }
           
    }
}
