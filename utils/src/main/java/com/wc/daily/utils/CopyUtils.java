package com.wc.daily.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;

/**
 * 复制工具类
 * Created by Administrator on 2016/5/20.
 */
public class CopyUtils {
    /**
     * 实现文本复制功能
     */
    public static void copy(Context context, CharSequence content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // cmb.setText(content.trim());
        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText("simple text", content);
        // Set the clipboard's primary clip.
        cmb.setPrimaryClip(clip);
    }

    /**
     * 实现粘贴功能
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // return cmb.getText().toString().trim();
        String resultString = "";
        if (!cmb.hasPrimaryClip()) {// 粘贴板没有内容

        } else {
            ClipData clipData = cmb.getPrimaryClip();
            int count = clipData.getItemCount();
            for (int i = 0; i < count; ++i) {
                ClipData.Item item = clipData.getItemAt(i);
                CharSequence str = item.coerceToText(context);
                Log.i("paste", "item : " + i + ": " + str);
                resultString += str;
            }

        }
        return resultString;
    }
}
