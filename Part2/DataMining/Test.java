import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test
{
    public static void main( String args[] ){
        String input = "140.138.123.12 - - [01/Feb/2014:00:09:49 +0800] \"GET http://www.yzu.edu.tw/ HTTP/1.1 200 12345";
        String IP_re =   "((?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5]))";
        String Date_re = "(?:\\d{2}\\/(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\/\\d{4})";
        String Time_re = "(?:[0-5][0-9]:[0-5][0-9]:[0-5][0-9])";
        String GMT_re = "(?:\\+(?:0[0-9]|1[0-2])00)";
        String HTTP_Method_re = "(HEAD|GET|POST|PUT|PATCH|DELETE)";
        String FullTime_re = "\\[(" + Date_re + ":" + Time_re + " " + GMT_re + ")\\]";
        String URL_re = "([-a-zA-Z0-9+&@#/%?=~_|!:,.;]+)";
        String HTTP_Version_re = "(HTTP\\/(?:0.9|1.0|1.1|2))";
        String Status_re = "([1-5]\\d{2})";
        String Packet_Size_re = "([0-9]+)";


        String RE = "^" + IP_re
                + " - - " + FullTime_re
                + " \"" + HTTP_Method_re
                + " " + URL_re + " "
                + HTTP_Version_re + " "
                + Status_re //combine whole regular expression
                + " " + Packet_Size_re + "$";

        if(input.matches(RE))
            System.out.println("Yes");
        else
            System.out.println("No");

        Pattern r = Pattern.compile(RE);
        Matcher m = r.matcher(input);
        if(m.find()){
            for(int i = 0; i <= m.groupCount(); i++){
                //if(i == 1|i == 6|i == 12|i == 13|i == 14|i == 16|i == 17)
                System.out.println(m.group(i));
            }
        }
        else
            System.out.println("NO MATCH");
    }
}
