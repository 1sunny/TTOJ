package one.sunny.commonutils;

public class DataBaseUtil {
    public static String redisSql(String... args){
        String result = "";
        for (int i = 0; i < args.length; i++){
            result += args[i];
            if (i != args.length-1){
                result += ":";
            }
        }
        return result;
    }
}
