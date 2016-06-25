package com.xiaoli.library.utils;

import android.content.Context;


/**
 *像素dip相互转换工具类
 *  xiaokx
 *  hioyes@qq.com
 *  2014-11-6
 */
public class DensityUtils {
    /**
     * 根据手机的分辨率将dp的单位转成px(像素)
     * param context
     * param dpValue
     * return
     */
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }

    /**
     * 根据手机的分辨率将px(像素)的单位转成dp
     * param context
     * param pxValue
     * return
     */
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
