package com.dld.autoviewpagerdemo.utils;

import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 * Created by song on 2016/11/15.
 */

public class StatusBarUtils {

    private static final String TAG =  "StatusBarUtils";

    private final static String STATUS_BAR_DEF_PACKAGE = "android";
    private final static String STATUS_BAR_DEF_TYPE = "dimen";
    private final static String STATUS_BAR_NAME = "status_bar_height";


    public static int getStatusBarHeight(Context context) {
        int statusHeight = 0;
        int resourceId = context.getResources().
                getIdentifier(STATUS_BAR_NAME, STATUS_BAR_DEF_TYPE, STATUS_BAR_DEF_PACKAGE);
        if (resourceId > 0) {
            statusHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        Log.d(TAG, String.format("statusHeight:%d", statusHeight));
        return statusHeight;
    }

    public static boolean isAllowTranslucentStatus(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            return false;
        }
        return true;
    }

}
