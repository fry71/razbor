/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import com.gtranslate.Translator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javazoom.jl.decoder.JavaLayerException;

/**
 *
 * @author dmitry
 */
public class specsParse {

    public static void main(String[] args) throws SQLException, IOException, InterruptedException, JavaLayerException {
        
            // mysql.parseSpecs();
            //mysql.parseSpecsScout();
            //mysql.parcPrice();
           mysql.getTranslate(7777, 8000);
       // testTrans();
            // mysql.getTowns();
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

    public static void testTrans() throws IOException, JavaLayerException {
        Translator translate = Translator.getInstance();
        Audio audio = Audio.getInstance();
        String text = "[Общая информация:; Этаж: 3 / 5; Общая площадь: 43 м²; Площадь комнат: 19+11 м²; Жилая площадь: 11 м²; Площадь кухни: 25 м²]".replaceAll("[+]","/");
        System.out.println(text);
        for (int i =1; i< text.split(";").length; i++){
            System.out.println(translate.translate(text.split(";")[i].split(":")[0], Language.RUSSIAN, Language.BELARUSIAN));
            
            System.out.println(translate.translate(text.split(";")[i].split(":")[1], Language.RUSSIAN, Language.BELARUSIAN));
        }
       //  text =  translate.translate(text, Language.RUSSIAN, Language.ENGLISH);
        //InputStream sound  = audio.getAudio(text, Language.RUSSIAN);
       // System.out.println(text); //Eu sou programador
        //audio.play(sound);
        


    }

    public static boolean checkWithRegExp(String userNameString) {
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
