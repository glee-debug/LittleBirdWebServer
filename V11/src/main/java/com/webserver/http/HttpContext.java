package com.webserver.http;

import java.util.HashMap;
import java.util.Map;

public class HttpContext {

    public static Map<String,String> mime = new HashMap<>();

    static{
        initMime();
    }

    private static void initMime(){
        mime.put("html","text/html");
        mime.put("HTML","text/html");
        mime.put("js","application/javascript");
        mime.put("css","text/css");
        mime.put("png","img/png");
        mime.put("jpg","img/jpeg");
        mime.put("gif","img/gif");
        mime.put("defaultType","font/woff2");
    }

    public static String getMime(String key){
        return mime.get(key);

    }
}
