package com.xiaoli.library.utils;

import android.content.Context;


/**
 * @ClassName: DensityUtils
 * @author xiaokx Email:hioyes@qq.com
 * @date 2014-11-6 下午8:17:55
 * @Description:像素dip相互转换工具类
 */
public class DensityUtils {
    /** 
     * 根据手机的分辨率将dp的单位转成px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率将px(像素)的单位转成dp 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
}
