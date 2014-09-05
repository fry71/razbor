/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mysql;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author fry
 */
public class googleApi {
    private static LinkedList data;
    public static void main(String[] args) throws SQLException, IOException, XPathExpressionException, ParserConfigurationException, SAXException, InterruptedException {
        data = new LinkedList<> ();
        mysql.doGoogleMaps(data, 3244);
       
    }
    
    
    
}

