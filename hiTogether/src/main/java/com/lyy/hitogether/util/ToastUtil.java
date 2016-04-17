package com.lyy.hitogether.util;

import android.widget.Toast;

import com.lyy.hitogether.global.App;

/**
 * Created by zoulux on 2016-04-16  19:38.
 */
public class ToastUtil {
    private static Toast toast;

    public static void message(String text) {
        if (toast == null) {
            toast = Toast.makeText(App.getInsatnce(), text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
    public static void message(int resId) {
        if (toast == null) {
            toast = Toast.makeText(App.getInsatnce(), resId, Toast.LENGTH_SHORT);
        } else {
            toast.setText(resId);
        }
        toast.show();
    }
}
