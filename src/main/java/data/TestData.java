package data;
import com.github.javafaker.Faker;
import java.util.Locale;

public class TestData {

    //courier data
    static Faker user = new Faker();
    static Faker ruUser = new Faker(new Locale("ru"));

    public static final String LOGIN = user.name().username() + System.currentTimeMillis();
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String FIRSTNAME = ruUser.name().firstName();

    //order data
    public static final String FIRST_NAME = "Даниил";
    public static final String LAST_NAME = "Бубличев";
    public static final String ADDRESS = "Санкт-Петербург";
    public static final String METRO_STATION = "4";
    public static final String PHONE = "+79999999999";
    public static final int RENT_TIME = 5;
    public static final String DELIVERY_DATE = "2026-07-15";
    public static final String COMMENT = "Не звонить";
}
