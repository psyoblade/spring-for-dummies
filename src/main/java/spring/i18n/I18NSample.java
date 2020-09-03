package spring.i18n;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <a href="https://www.baeldung.com/java-resourcebundle">A Guide to the ResourceBundle</a>
 * <a href="https://www.baeldung.com/java-8-localization">Internationalization and Localization in Java 8</a>
 * <a href="https://dzone.com/articles/a-beginners-guide-to-java-internationalization">A Beginner’s Guide to Java Internationalization</a>
 */
public class I18NSample {
    static String bundleName = "BigBrotherResourceBundle";

    public I18NSample() {

    }

    public void storeSample() {

    }

    public void sayHello(String language, String country) {
        Locale currentLocale;
        ResourceBundle messages;

        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle(bundleName, currentLocale);
        System.out.println(messages.getString("greetings"));
        System.out.println(messages.getString("inquiry"));
        System.out.println(messages.getString("farewell"));
    }

    public static void main(String[] args) {
        String language;
        String country;

        if (args.length != 2) {
            language = "ko";
            country = "KR";
        } else {
            language = args[0];
            country = args[1];
        }

        I18NSample i18n = new I18NSample();
        i18n.storeSample();
        i18n.sayHello(language, country);
    }

}
