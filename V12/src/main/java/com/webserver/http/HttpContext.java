package com.webserver.http;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpContext {

    public static Map<String,String> mime = new HashMap<>();

    static{
        initMime();
    }

    private static void initMime(){
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read("./config/web.xml");
            Element element = doc.getRootElement();
            List<Element> mimeMapping = element.elements("mime-mapping");
            for (Element e:mimeMapping) {
                String key = e.elementText("extension");
                String value = e.elementText("mime-type");
                mime.put(key,value);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static String getMime(String key){
        return mime.get(key);

    }
}
