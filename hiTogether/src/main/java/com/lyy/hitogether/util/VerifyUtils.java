package com.lyy.hitogether.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zoulux on 2016-04-27  23:27.
 */
public class VerifyUtils {
    private static final String phonePattern = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static boolean isPhone(String mobiles) {
        if (TextUtils.isEmpty(mobiles))
            return false;
        Pattern p = Pattern.compile(phonePattern);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
