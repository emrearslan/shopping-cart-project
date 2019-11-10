package com.ea.shop.util;

import java.util.Locale;

public class StringUtil {

    public static final String DEFAULT_LOCALE = "tr";
    public static final String PERCENT = "%";
    public static final String SLASH = "/";

    public static boolean isEmpty(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static String like(String str) {
        String searchStr = str.trim().toLowerCase(new Locale(DEFAULT_LOCALE));
        return likeWithoutLowerCase(searchStr);
    }

    public static String likeWithoutLowerCase(String str) {
        return (str == null) ? null : (PERCENT + str + SLASH);
    }

}
