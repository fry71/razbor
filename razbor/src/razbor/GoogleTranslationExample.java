/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package razbor;

import com.translator.google.core.ProxyWrapper;
import com.translator.google.core.Translation;
import com.translator.google.translator.OnlineGoogleTranslator;
import com.translator.google.translator.OnlineGoogleTranslator.Language;
import java.io.IOException;

public class GoogleTranslationExample
{
        public static void main(String[] args) throws IOException
        {
                // general constants
                final String SOURCE_TEXT = "Hello, world!";
                final Language SOURCE_LANGUAGE = Language.ENGLISH;
                final Language TARGET_LANGUAGE = Language.RUSSIAN;

                // specific setting only for Google Translate API v2
                final String API_KEY = "AIzaSyDAFAewLAZfXo97AigqS9qq0iZx2BrKswQ";

                // specific settings only for connection through proxy
                final String PROXY_HOST = "192.168.0.1";
                final int PROXY_PORT = 3128;
                final String PROXY_USERNAME = "User";
                final String PROXY_PASSWORD = "Password";

                // ***********************
                // Google Translate API v1
                // ***********************
                // using Google Translate API v1 without proxy
                OnlineGoogleTranslator translator = OnlineGoogleTranslator.createInstance();
                Translation translation = translator.translate(SOURCE_TEXT, SOURCE_LANGUAGE, TARGET_LANGUAGE);

//                System.out.printf("In %s text: %s\n", SOURCE_LANGUAGE, SOURCE_TEXT);
//                System.out.printf("In %s text: %s\n", TARGET_LANGUAGE, translation.getTranslatedText());
//
//                // using Google Translate API v1 with connection through proxy without authentication
//                translator = OnlineGoogleTranslator.createInstance(new ProxyWrapper(PROXY_HOST, PROXY_PORT));
//                translation = translator.translate(SOURCE_TEXT, SOURCE_LANGUAGE, TARGET_LANGUAGE);
//
//                System.out.printf("In %s text: %s\n", SOURCE_LANGUAGE, SOURCE_TEXT);
//                System.out.printf("In %s text: %s\n", TARGET_LANGUAGE, translation.getTranslatedText());
//
//                // using Google Translate API v1 with connection through proxy with authentication
//                translator = OnlineGoogleTranslator.createInstance(new ProxyWrapper(PROXY_HOST, PROXY_PORT, PROXY_USERNAME, PROXY_PASSWORD));
//                translation = translator.translate(SOURCE_TEXT, SOURCE_LANGUAGE, TARGET_LANGUAGE);
//
//                System.out.printf("In %s text: %s\n", SOURCE_LANGUAGE, SOURCE_TEXT);
//                System.out.printf("In %s text: %s\n", TARGET_LANGUAGE, translation.getTranslatedText());

                // ***********************
                // Google Translate API v2
                // ***********************
                // using Google Translate API v2 without proxy
                translator = OnlineGoogleTranslator.createInstance(API_KEY);
                translation = translator.translate(SOURCE_TEXT, SOURCE_LANGUAGE, TARGET_LANGUAGE);

                System.out.printf("In %s text: %s\n", SOURCE_LANGUAGE, SOURCE_TEXT);
                System.out.printf("In %s text: %s\n", TARGET_LANGUAGE, translation.getTranslatedText());

                // using Google Translate API v2 with connection through proxy without authentication
                translator = OnlineGoogleTranslator.createInstance(new ProxyWrapper(PROXY_HOST, PROXY_PORT), API_KEY);
                translation = translator.translate(SOURCE_TEXT, SOURCE_LANGUAGE, TARGET_LANGUAGE);

                System.out.printf("In %s text: %s\n", SOURCE_LANGUAGE, SOURCE_TEXT);
                System.out.printf("In %s text: %s\n", TARGET_LANGUAGE, translation.getTranslatedText());

                // using Google Translate API v2 with connection through proxy with authentication
                translator = OnlineGoogleTranslator.createInstance(new ProxyWrapper(PROXY_HOST, PROXY_PORT, PROXY_USERNAME, PROXY_PASSWORD), API_KEY);
                translation = translator.translate(SOURCE_TEXT, SOURCE_LANGUAGE, TARGET_LANGUAGE);

                System.out.printf("In %s text: %s\n", SOURCE_LANGUAGE, SOURCE_TEXT);
                System.out.printf("In %s text: %s\n", TARGET_LANGUAGE, translation.getTranslatedText());
        }
}
