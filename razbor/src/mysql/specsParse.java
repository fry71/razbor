/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static mysql.mysql.conn;

/**
 *
 * @author dmitry
 */
public class specsParse {

    public static void main(String[] args) throws SQLException, IOException {
        mysql.parseSpecs();
       // mysql.getPics(1958, 2000);
       //mysql.getPics(2069, 3000);
        //mysql.getPics(14270, 15000);
        
       // System.out.println(getTypeOfDayWithSwitchStatement("Санузел"));
//        System.out.println("Cool check:");  
//          
//        System.out.println(checkWithRegExp("_@BEST"));  
//        System.out.println(checkWithRegExp("vovan asdasdasd"));  
//        System.out.println(checkWithRegExp("vo"));  
//        System.out.println(checkWithRegExp("31123"));  
          
       
    }
    
    
    public static boolean checkWithRegExp(String userNameString){  
        Pattern p = Pattern.compile("^[a-zА-Яа-я\\s]{3,20}$");  
        Matcher m = p.matcher(userNameString);  
        return m.matches();  
        
        
    }
    public static String getTypeOfDayWithSwitchStatement(String dayOfWeekArg) {
      String spec_name_id = null;
            switch (dayOfWeekArg) {
                case "Жилая площадь":
                    spec_name_id = "1";
                    break;
                case "Тип продажи":
                    spec_name_id = "2";
                    break;
                case "Общая площадь":
                    spec_name_id = "3";
                    break;
                case "Санузел":
                    spec_name_id = "4";
                    break;
                case "Вид из окна":
                    spec_name_id = "5";
                    break;
                case "Площадь кухни":
                    spec_name_id = "6";
                    break;
                case "Площадь комнат":
                    spec_name_id = "7";
                    break;
                case "Этаж":
                    spec_name_id = "8";
                    break;
                case "Лифт":
                    spec_name_id = "9";
                    break;
                case "Балкон":
                    spec_name_id = "10";
                    break;
                case "Тип дома":
                    spec_name_id = "11";
                    break;
        }
            return spec_name_id;
}
}


