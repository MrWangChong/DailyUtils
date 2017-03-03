package com.wc.daily.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话验证工具类
 * Created by Administrator on 2017/3/3.
 */

public class PhoneUtils {
    /**
     * 手机号验证
     *
     * @return 验证通过返回true
     */
    public static boolean isMobilePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 座机号验证
     *
     * @return 验证通过返回true
     */
    public static boolean isTelephone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return false;
        }
        if (phone.length() > 9) {
            Pattern p = Pattern.compile("^[0][1-9]{2,3}[-, ,0-9][0-9]{5,10}$");  // 验证带区号的
            Matcher m = p.matcher(phone);
            return m.matches();
        } else {
            Pattern p = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }
}
