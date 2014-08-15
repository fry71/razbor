/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication6;
import java.io.IOException;

/**
 *
 * @author fry
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class JavaApplication6 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        Document doc = Jsoup.connect("http://www.cian.ru/cat.php?deal_type=2&obl_id=1&room1=1").get();
        Elements items = doc.select("tr cat");
        Elements category = doc.getElementsByTag("tr");
        int i = 0;
        for (Element item : category) {
            if (item.attr("class").equalsIgnoreCase("cat"))
                //for (item.getElementsMatchingOwnText(null))
           System.out.println( item.getElementsMatchingText("") );
            i++;
        }
        }

    
}    
    
    

