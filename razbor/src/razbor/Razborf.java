/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package razbor;

import com.sun.org.apache.xerces.internal.dom.DOMNormalizer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;


/**
 *
 * @author dmitry
 */
public class Razborf {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {
       File file = new File("/home/dmitry/data.txt");
        FileInputStream fis = new FileInputStream(file);
         StringBuilder sb = new StringBuilder();
         
         try {
        //Объект для чтения файла в буфер
        BufferedReader in = new BufferedReader(new FileReader( file.getAbsoluteFile()));
        try {
            //В цикле построчно считываем файл
            String s;
            int a =1;
            System.out.println(s = in.readLine());
            System.out.println("var data = {");
            while ((s = in.readLine()) != null) {
                try {
                    String mass[] = s.split(" ");
                   //System.out.print(a++ +" ");
                String tmp = "";
                System.out.print(s.split(" ")[4]+ " "); tmp +=s.split(" ")[4]+ " ";
                System.out.print(s.split(" ")[5]+ " "); tmp +=s.split(" ")[5]+ " ";
                System.out.print(s.split(" ")[6]+ " "); tmp +=s.split(" ")[6]+ " ";
                System.out.print(s.split(" ")[7]+ " "); tmp +=s.split(" ")[7]+ " ";
                if(!s.split(" ")[8].matches("\\d\\D\\D\\D|\\d\\d\\D\\D\\D")){
                   System.out.print(s.split(" ")[8]+ " "); 
                   tmp +=s.split(" ")[8]+ " ";
                }
                    System.out.print("; ");
                    System.out.print(GeocodingSample.getAddress(tmp));
                System.out.println(""); 
                TimeUnit.SECONDS.sleep(1);
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                }
                
                
              
                
            }
        } finally {
            //Также не забываем закрыть файл
            in.close();
        }
    } catch(IOException e) {
        throw new RuntimeException(e);
    }
    }
    
}
