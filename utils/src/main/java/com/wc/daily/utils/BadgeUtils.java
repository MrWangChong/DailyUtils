package com.wc.daily.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;


/**
 * 小角标工具类(不完全)
 * Created by Administrator on 2016/12/29.
 */

public class BadgeUtils {
    
    private static BadgeUtils mBadgeUtils;

    public synchronized static BadgeUtils getInstence() {
        if (mBadgeUtils == null) {
            mBadgeUtils = new BadgeUtils();
        }
        return mBadgeUtils;
    }

    private BadgeUtils() {

    }

    public void setBadge(Context context, int count) throws Exception {
        String brand = android.os.Build.BRAND;
        Log.v("BadgeUtils", "brand=" + brand);
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        Intent intent;
        if ("vivo".equalsIgnoreCase(brand)) { //VIVO
            intent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            intent.putExtra("packageName", context.getPackageName());
            intent.putExtra("className", launcherClassName);
            intent.putExtra("notificationNum", count);
            context.sendBroadcast(intent);
        } else if ("miui".equalsIgnoreCase(brand)||"Xiaomi".equalsIgnoreCase(brand)) { //MIUI
            try {
                Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
                Object miuiNotification = miuiNotificationClass.newInstance();
                Field field = miuiNotification.getClass().getDeclaredField("messageCount");
                field.setAccessible(true);
                field.set(miuiNotification, String.valueOf(count == 0 ? "" : count));  // 设置信息数-->这种发送必须是miui 6才行
            } catch (Exception e) {
                e.printStackTrace();
                // miui 6之前的版本
                Intent localIntent = new Intent(
                        "android.intent.action.APPLICATION_MESSAGE_UPDATE");
                localIntent.putExtra(
                        "android.intent.extra.update_application_component_name",
                        context.getPackageName() + "/" + getLauncherClassName(context));
                localIntent.putExtra(
                        "android.intent.extra.update_application_message_text", String.valueOf(count == 0 ? "" : count));
                context.sendBroadcast(localIntent);
            }
//            //MIUI6以下
//            intent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
//            intent.putExtra("android.intent.action.APPLICATION_MESSAGE_UPDATE", "your app packageName/.LAUNCHER ActivityName");
//            intent.putExtra("android.intent.extra.update_application_message_text", count);
//            context.sendBroadcast(intent);
//            //MIUI6以上
//            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
//            Notification notification = builder.build();
//            try {
//                Object miuiNotification = Class.forName("android.app.MiuiNotification").newInstance();
//                Field field = miuiNotification.getClass().getDeclaredField("messageCount");
//                field.setAccessible(true);
//                field.set(miuiNotification, count);
//                notification.getClass().getField("extraNotification").set(notification, miuiNotification);
//                managerCompat.notify(0, notification);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        } else if ("samsung".equalsIgnoreCase(brand)) {//三星
            intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", count);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
        } else if ("huawei".equalsIgnoreCase(brand)||"honor".equalsIgnoreCase(brand)) {//华为
            Bundle extra = new Bundle();
            extra.putString("package", context.getPackageName());
            extra.putString("class", launcherClassName);
            extra.putInt("badgenumber", count);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, extra);
        } else if ("sony".equalsIgnoreCase(brand)) {//Sony
            intent = new Intent();
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", count > 0);
            intent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", launcherClassName);
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", count < 1 ? "" : count);
            intent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());
            context.sendBroadcast(intent);
        } else if ("oppo".equalsIgnoreCase(brand)) { //OPPO
            try {
                Bundle extras = new Bundle();
                extras.putInt("app_badge_count", count);
                context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", String.valueOf(count), extras);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }

//        <uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE"/>
//        <uses-permission android:name="com.sec.android.provider.badge.permission.READ"/>
//        <uses-permission android:name="com.sec.android.provider.badge.permission.WRITE"/>
//        <uses-permission android:name="com.huawei.android.launcher.permission.CHANGE_BADGE"/>
    }

    private String getLauncherClassName(Context context) {
        if (context == null) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        try {
            for (ResolveInfo resolveInfo : pm.queryIntentActivities(intent, 0)) {
                if (resolveInfo.activityInfo.applicationInfo.packageName.equalsIgnoreCase(context.getPackageName())) {
                    return resolveInfo.activityInfo.name;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
}

