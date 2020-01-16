package com.leisurely.spread.util;

import java.util.HashMap;
import java.util.Iterator;


public class SignUtils {

    public static String getUrl(HashMap<String, String> params) {
        String url = "";
        // 添加url参数
        if (params != null&&!params.isEmpty()) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }


}
