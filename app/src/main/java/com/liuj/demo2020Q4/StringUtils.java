package com.liuj.demo2020Q4;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author liuj
 * @time 2020/12/30
 */

public class StringUtils {
    public static final char[] ELLIPSIS_NORMAL = { '\u2026' }; // this is "..."
    public static final String ELLIPSIS_STRING = new String(ELLIPSIS_NORMAL);

    public static final String EMPTY = "";

    public static boolean isBlank(CharSequence str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    public static String join(String[] array, String separator) {
        return join(Arrays.asList(array), separator);
    }

    public static String join(Collection collection, String separator) {
        if (collection == null) {
            return null;
        }
        return join(collection.iterator(), separator);
    }

    public static String join(Iterator iterator, String separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        Object first = iterator.next();
        if (!iterator.hasNext()) {
            return first == null ? "" : first.toString();
        }

        // two or more elements
        StringBuffer buf = new StringBuffer(256); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }

        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public static String replace(String text, String searchString, String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuffer buf = new StringBuffer(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 一个英文和一个汉字都算1，两个英文也算1
     * @param str
     * @return
     */
    public static int getChineseLen(String str) {
        int len = 0;
        int enCount = 0;
        for (int i = 0; i < str.length(); i++) {
            int code = str.codePointAt(i);
            if (code >= 0 && code <= 0xff) {
                enCount++;
            } else {
                len++;
            }
        }
        return len + enCount / 2 + enCount % 2;
    }

    public static String removeLeadingSign(String rawString, char sign) {
        if (StringUtils.isBlank(rawString)) {
            return rawString;
        }
        int st = 0;
        int len = rawString.length();
        while ((st < len) && (rawString.charAt(st) == sign)) {
            st++;
        }
        return ((st > 0) || (len < rawString.length())) ? rawString.substring(st, len) : rawString;
    }

    public static int parseInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static Long parseLong(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0L;
        }
    }


    public static boolean stringEquals(String arg1, String arg2) {
        return Objects.equals(arg1, arg2);
    }
}