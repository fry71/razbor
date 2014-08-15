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
public class HTMLParserExample1 {

    public static void main(String[] args) throws IOException {
           
        Document doc = Jsoup.connect("http://www.funda.nl/europe/heel-europa/1-10-kamers/")
                .data("query", "Java")
                .userAgent("Mozil")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
            Elements items = doc.select("ul.object-list  > li");
            for (Element item : items) {
            
           System.out.println( item );
            break;
        }
           
    }
}
