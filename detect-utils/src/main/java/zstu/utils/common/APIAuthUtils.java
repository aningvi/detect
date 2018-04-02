package zstu.utils.common;

import java.util.regex.Pattern;

/**
 * Created by liupeizhi on 2017/11/22.
 */
public class APIAuthUtils {

    public static boolean checkURL(String uri,String realUri){
        if(StringUtil.isEmpty(uri)|| StringUtil.isEmpty(realUri)){
            return false;
        }
        try {
            return Pattern.matches(uri, realUri);
        }catch (Exception e){
            return false;
        }
    }

    public static void main(String[] args){
        System.out.println(checkURL("/v1/user/.*/.*/.*","/v1/user/a/b/c"));
        System.out.println(checkURL("/v1/user/.*/.*/.*","/v1/user/a/b"));
        System.out.println(checkURL("/v1/user/.*/.*/.*","/v1/user/a"));
    }
}
