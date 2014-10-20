/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package razbor;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import static com.sun.org.apache.xalan.internal.lib.ExsltStrings.split;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;
import static razbor.ParseUrls.England;

/**
 *
 * @author dmitry
 */
public class ParseUrls {

    final static Geocoder geocoder = new Geocoder();

    public static void main(String[] args) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {

        try {
            System.out.println(razbor.GeocodingSample.getAddress("Nyhavn 12A, 2."));

//            GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress("Paris, France").setLanguage("en").getGeocoderRequest();
//            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
//            System.out.println(geocoderResponse.getResults().isEmpty());
        } catch (Exception ex) {
            Logger.getLogger(ParseUrls.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Bulgary() throws Exception {

        for (int i = 2; i <= 500; i++) {
            Document doc = Jsoup.connect("http://www.cian.ru/cat.php?deal_type=2&obl_id=1&city[0]=1&room1=1&room2=1&room3=1&room4=1&room5=1&room6=1&p=" + i)
                    .data("query", "Java")
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
                    .cookie("auth", "true")
                    .timeout(3000)
                    .post();
            Elements items = doc.select("tr.cat > td");

            for (Element item : items) {
                try {

                    String tmp = "";
                    String s = null;

                    System.out.print(s.split(" ")[4] + " ");
                    tmp += s.split(" ")[4] + " ";
                    System.out.print(s.split(" ")[5] + " ");
                    tmp += s.split(" ")[5] + " ";
                    System.out.print(s.split(" ")[6] + " ");
                    tmp += s.split(" ")[6] + " ";
                    System.out.print(s.split(" ")[7] + " ");
                    tmp += s.split(" ")[7] + " ";
                    if (!s.split(" ")[8].matches("\\d\\D\\D\\D|\\d\\d\\D\\D\\D")) {
                        System.out.print(s.split(" ")[8] + " ");
                        tmp += s.split(" ")[8] + " ";
                    }
                    System.out.print("; ");
                    System.out.print(GeocodingSample.getAddress(tmp));
                    System.out.println("");
                    TimeUnit.SECONDS.sleep(1);
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    public static void England() throws Exception {
        for (int i = 1; i <= 500; i++) {
            Document doc = Jsoup.connect("http://www.zoopla.co.uk/for-sale/property/oxford/?include_retirement_homes=true&q=Oxford%2C%20Oxfordshire&new_homes=include&include_shared_ownership=true&search_source=home&radius=0&pn=" + i)
                    .data("query", "Java")
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
                    .cookie("auth", "true")
                    .timeout(3000)
                    .post();
            Elements items = doc.select("a.listing-results-address");

            for (Element item : items) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print(item.text() + ";");
                String temp = GeocodingSample.getAddress(item.text());

                System.out.print(temp.split(";")[0]);
                System.out.print(",");
                System.out.print(temp.split(";")[1]);
                System.out.println("");
            }
        }
    }

    public static void Holland() throws Exception {
        System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        System.out.println("<markers>");
        for (int i = 1; i <= 500; i++) {
            Document doc = Jsoup.connect("http://www.funda.nl/koop/provincie-limburg/1-10-kamers/p" + i + "/")
                    .data("query", "Java")
                    .userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2049.0 Safari/537.36")
                    .cookie("auth", "true")
                    .timeout(3000)
                    .post();
            Elements items = doc.select("ul.properties-list > li");

            TimeUnit.SECONDS.sleep(1);
            for (Element item : items) {
                System.out.print("<marker>");
                System.out.print("<adress>");
                System.out.print(item.text());

                System.out.print("</adress>");
                String temp = GeocodingSample.getAddress(item.text());
                System.out.print("<lat>");
                System.out.print(temp.split(";")[0]);
                System.out.print("</lat>");
                System.out.print("<lng>");
                System.out.print(temp.split(";")[1]);
                System.out.print("</lng>");
                System.out.print("</marker>");
                System.out.println("");
                //String mass[] = item.text().split(" ");
                break;

            }

        }
        System.out.println("<markers>");

    }

}
