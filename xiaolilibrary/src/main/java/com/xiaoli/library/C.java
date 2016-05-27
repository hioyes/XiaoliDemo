package com.xiaoli.library;


import android.app.Activity;
import android.os.Environment;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName: HttpWrapper
 * @date 2014-11-6 下午8:17:55
 * @Description:常量、系统方法基类
 */
public abstract class C {

    /**
     * 当前Activity
     */
    public static Activity mCurrentActivity;

    public static String LOG_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/lbanalytics/log/";//日志文件存放跟目录


    /**
     * 网络请求超时
     */
    public static final int NET_TIME_OUT = 90000;

    /**
     * 请求数据为空
     */
    public static final int DATA_IS_NULL = 90001;

    /**
     * 未连接网络
     */
    public static final int NET_IS_NULL = 90002;

    /**
     * 已连接WIFI
     */
    public static final int NET_IS_WIFI = 90003;

    /**
     * 连接的WIFI不可用
     */
    public static final int NET_IS_WIFI_UNABLE = 90004;


    /**
     * 已连接移动数据
     */
    public static final int NET_IS_MOBLE = 90005;

    /**
     * 连接的移动数据不可用
     */
    public static final int NET_IS_MOBLE_UNABLE = 90006;

    /**
     * URL地址找不到
     */
    public static final int NET_FILE_NOT_FOUND = 90007;


    /**
     * 资源释放
     * @param activity
     * @param resid 空资源文件ID
     */
    public static void release(Activity activity,int resid) {
        if(activity==null)return;
        Field[] fields = activity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field == null) continue;
            if (Modifier.isFinal(field.getModifiers())) continue;
            field.setAccessible(true);//关闭安全检查
            try {
                String typeString = field.getType().toString();
                if (typeString.startsWith("class")) {
                    field.set(activity, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if(resid!=0)
            activity.setContentView(resid);
    }

}
