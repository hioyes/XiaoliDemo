package com.xiaolidemo;

import android.os.Environment;

import com.xiaoli.library.C;

/**
 * 全局配置文件
 * xiaokx
 * hioyes@qq.com
 * 2016-6-17
 */
public class Config {
    /**
     * 生产环境，1：测试，2准生产，3：生产
     */
    public static final int PRODUCTION_ENVIRONMENT = 2;

    public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String DB_PATH = PATH + "/Android/data/lubaocar/db/";// 数据库存储位置
}
