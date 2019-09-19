package com.zzg.mybatis.generator.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Owen on 6/18/16.
 */
public class MyStringUtils {

    public static String ignorePrefixStr(String str){
        String delimiter = "_";
        if(StringUtils.isNotBlank(str)){
            int delimiterPos = str.indexOf(delimiter);
            if(delimiterPos > 0){
                int startPos = Math.min(delimiterPos + 1, str.length());
                return str.substring(startPos);
            }
        }
        return str;
    }

    /**
     *
     * convert string from slash style to camel style, such as my_course will convert to MyCourse
     *
     * @param str
     * @return
     */
    public static String dbStringToCamelStyle(String str) {
        if (str != null) {
            if (str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toUpperCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }

}
