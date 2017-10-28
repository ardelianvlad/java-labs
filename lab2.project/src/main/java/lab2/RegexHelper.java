package lab2;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {
    public final static String NAME_REGEX = "name: ([A-Za-z1-9 ].{0,100}?);";
    public final static String PRODUCTION_REGEX = "production: (\\d{4}-\\d{2}-\\d{2});";
    public final static String EXPIRATION_REGEX = "production: (\\d{4}-\\d{2}-\\d{2});";
    public final static String CATEGORY_REGEX = "category: ([A-Z]+);";
    public final static String PRICE_REGEX = "price: (\\d*.\\d*)";
    public final static String PRODUCTS_REGEX = "products: (.+)";

    /**
     * @param input String to test
     * @param regex Regex to use for testing
     * @return Last group that matches specified regex
     */
    public static String getRegexGroup(String input, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher match = p.matcher(input);
        if (!match.find())
            throw new IllegalArgumentException();
        return match.group(1);
    }

}