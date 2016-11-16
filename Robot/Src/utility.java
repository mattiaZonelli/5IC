package other;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Rikkardo on 26/10/2016.
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

    public static String[] getStrings(String data, String begin, String end, String separator){
        return data.substring(data.indexOf(begin)+begin.length(), data.indexOf(end)).split(separator);
    }

    public static boolean[] getBooleans(String data, String begin, String end, String separator){
        String[] temp = getStrings(data, begin, end, separator);
        boolean[] result = new boolean[temp.length];
        for(int i = 0; i<temp.length; i++){
            if(temp[i].equals("1")){
                result[i] = true;
            } else{
                result[i] = false;
            }
        }
        return result;
    }

    public static int[] getInts(String data, String begin, String end, String separator){
        String[] temp = getStrings(data, begin, end, separator);
        int[] result = new int[temp.length];
        for(int i = 0; i<temp.length; i++){
            result[i] = Integer.parseInt(temp[i]);
        }
        return result;
    }

    /*
        From xml file create ArrayList<String> of values between "<tag>" and "</tag>" if on the same line
     */
    public static ArrayList<String> createMessages(String fileName, String tag){
        ArrayList<String> messages = new ArrayList<>();
        String openTag = "<" + tag + ">";
        String closeTag = "</" + tag + ">";
        try {
            Scanner s = new Scanner(new File(fileName));
            String line = "";
            String message = "";
            boolean composed = false;
            while(s.hasNext()){
                line = s.nextLine();
                if(line.contains(openTag) && line.contains(closeTag)){
                    messages.add(line.substring(line.indexOf(openTag)+openTag.length(), line.indexOf(closeTag)));
                } else if(line.contains(openTag)){
                    message = line.substring(line.indexOf(openTag)+openTag.length());
                } else if(line.contains(closeTag)){
                    message += line.replace(closeTag, "");
                    messages.add(message);
                } else {
                    message += line;
                }

            }
        } catch(FileNotFoundException e){
            System.out.println("File route " + fileName + " not found.");
        }
        return messages;
    }

    public static String getTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("HH_mm_ss");
        return f.format(c.getTime());
    }

}
