import java.util.Vector;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile
{
    static Vector<String> FileRead(int num_log){
        Vector log = new Vector();
        try (FileReader fr = new FileReader("logWithOid/log"+num_log)) {
            BufferedReader br = new BufferedReader(fr);
            while(br.ready()){
                    log.add(br.readLine());
                    //System.out.println(log.get(log.size()-1));
            }
        }
        catch(IOException err){
            System.out.println(err);
        }

        return log;
    }

    static Vector<String> RegularExpression(String log){
        //String input = "140.138.123.12 - - [01/Feb/2014:00:09:49 +0800] \"GET http://www.yzu.edu.tw/ HTTP/1.1\" 200 12345";

        String IP_re =   "((?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                "(?:[01]?\\d\\d?|2[0-4]\\d|25[0-5]))";
        String Date_re = "(?:\\d{2}\\/(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\/\\d{4})";
        String Time_re = "(?:[0-5][0-9]:[0-5][0-9]:[0-5][0-9])";
        String GMT_re = "(?:\\+(?:0[0-9]|1[0-2])00)";
        String HTTP_Method_re = "(HEAD|GET|POST|PUT|PATCH|DELETE)";
        String FullTime_re = "\\[(" + Date_re + ":" + Time_re + " " + GMT_re + ")\\]";
        String URL_re = "([-a-zA-Z0-9+&@#/%?=~_|!:,.;<>[\\u4e00-\\u9fa5]]+)";
        String HTTP_Version_re = "(HTTP\\/(?:0.9|1.0|1.1|2))";
        String Status_re = "([1-5]\\d{2})";
        String Packet_Size_re = "((?:[0-9]+)|(?:\\-))";

        String RE = "^" + IP_re
                + " - - " + FullTime_re
                + " \"" + HTTP_Method_re
                + " " + URL_re + " "
                + HTTP_Version_re + "\" "
                + Status_re //combine whole regular expression
                + " " + Packet_Size_re + "$";  //所有RE整合

        if (!log.matches(RE)) //若沒有match成功，則回傳null
            return null;

        Vector<String> Log = new Vector(); //一筆資料做切割後所得到的欄位
        Pattern r = Pattern.compile(RE);
        Matcher m = r.matcher(log);
        if(m.find()){
            for(int i = 0; i <= m.groupCount(); i++){
                Log.add(m.group(i));
            }
        }
        else
            System.out.println("NO MATCH");/**/

        return Log;
    }

    public static void main( String args[] ){
        int total_fail = 0;
        for(int i = 1; i < 3; i++) {
            Vector<Vector<String>> result=new Vector(); //result of one log file
            Vector<String> log = FileRead(i); //一次讀一個log檔
            int Fail_count = 0; //記錄此log檔沒有match的數量
            for(int j = 0; j < log.size(); j++) {
                if(RegularExpression(log.get(j))==null) //檢查沒有match成功的數量
                    Fail_count++;
                else {
                    result.add(RegularExpression(log.get(j)));
                    System.out.println(result.get(result.size()-1).get(0));
                }
            }
            System.out.println(Fail_count); //此log檔沒有match到的數量
            total_fail+=Fail_count;
            /*for(int x = 0; x < result.size(); x++) {
                for(int y = 0; y < result.get(x).size(); y++) {
                    System.out.println(result.get(x).get(y));
                }
                System.out.println("------------------");
            }*/
        }
        System.out.println("Total Fail of RE "+total_fail);
    }
}


