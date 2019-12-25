package luubieunghi.lbn.booklib.Utility.Others;

public class StringUtils {
    //stringMaxLength này là tính cả dấu ba chấm (nếu có).
    public static String cropString(String string, int stringMaxLength){
        StringBuilder stringBuilder = new StringBuilder(string);
        String res;
        if (stringMaxLength > string.length()){
            res = string;
        }
        else{
            res = stringBuilder.substring(0, stringMaxLength - 3);
            StringBuilder builder = new StringBuilder(res);
            builder.append("...");
            res = builder.toString();
        }
        return res;
    }
}
