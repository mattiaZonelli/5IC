package other;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rikkardo on 28/11/2016.
 */
public class utility {

    public static String ipRegex = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
            "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final String IPADDRESS_PATTERN = ipRegex;
    public static Pattern ipPattern = Pattern.compile(IPADDRESS_PATTERN);

    public static boolean validIp(String address){
        Matcher m = ipPattern.matcher(address);
        return m.matches();
    }

    public static String wordAt(String words, int index){
        String word = "";
        int i = 0;
        Scanner s = new Scanner(words);
        while(s.hasNext() && i<=index){
            if(i==index){
                word = s.next();
            } else {
                s.next();
            }
            i++;
        }
        return word;
    }
}
