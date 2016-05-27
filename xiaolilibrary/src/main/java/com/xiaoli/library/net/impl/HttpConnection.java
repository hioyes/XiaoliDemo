package com.xiaoli.library.net.impl;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.xiaoli.library.C;
import com.xiaoli.library.net.CommonHandler;
import com.xiaoli.library.net.INetwork;
import com.xiaoli.library.net.InternetUtils;
import com.xiaoli.library.utils.DateUtils;
import com.xiaoli.library.utils.HttpUtils;
import com.xiaoli.library.utils.StringUtils;
import com.xiaoli.library.utils.ThreadPoolUtils;
import com.xiaoli.library.utils.UploadUtils;

import java.io.File;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaokx on 2016-4-21 10:00
 * @Email:hioyes@qq.com
 */
public class HttpConnection implements INetwork {
    @Override
    public boolean checkNetWorking(Context context, Handler handler) {
        if(!InternetUtils.checkNet(context)){
            Message msg = handler.obtainMessage();
            msg.what = C.NET_IS_NULL;
            msg.obj = "未连接网络";
            handler.sendMessage(msg);
            return false;
        }
        int netType = InternetUtils.getConnectedType(context);
        if(netType==0){
            //连接到移动网络
            if(!InternetUtils.isMobileConnected(context)){
                Message msg = handler.obtainMessage();
                msg.what = C.NET_IS_MOBLE_UNABLE;
                msg.obj = "移动网络不可用";
                handler.sendMessage(msg);
                return false;
            }
        }else if(netType==1){
            //连接到wifi
            if(!InternetUtils.isWifiConnected(context)){
                Message msg = handler.obtainMessage();
                msg.what = C.NET_IS_WIFI_UNABLE;
                msg.obj = "WIFI不可用";
                handler.sendMessage(msg);
                return false;
            }
        }else{
            Message msg = handler.obtainMessage();
            msg.what = C.NET_IS_NULL;
            msg.obj = "非WIFI，非移动网络";
            handler.sendMessage(msg);
            return false;
        }
        return true;
    }

    @Override
    public void processResult(String result, int taskid, Handler handler) {
        try {
            String fileContent = result;
            if (StringUtils.isNullOrEmpty(result)) {
                Message msg = handler.obtainMessage();
                msg.what = taskid;
                msg.obj = "http wrapper check data is null";
                handler.sendMessage(msg);
                fileContent = msg.obj.toString();
            } else if (result.equals(HttpUtils.TIME_OUT)) {
                Message msg = handler.obtainMessage();
                msg.what = C.NET_TIME_OUT;
                msg.obj = "http wrapper check data is null->TIME_OUT";
                handler.sendMessage(msg);
                fileContent = msg.obj.toString();
            } else if (result.equals(HttpUtils.FILE_NOT_FOUND)) {
                Message msg = handler.obtainMessage();
                msg.what = C.NET_FILE_NOT_FOUND;
                msg.obj = "http wrapper check data is null->FILE_NOT_FOUND";
                handler.sendMessage(msg);
                fileContent = msg.obj.toString();
            } else if (result.equals(HttpUtils.SERVER_NOT_FOUND)) {
                Message msg = handler.obtainMessage();
                msg.what = C.NET_FILE_NOT_FOUND;
                msg.obj = "http wrapper check data is null->SERVER_NOT_FOUND";
                handler.sendMessage(msg);
                fileContent = msg.obj.toString();
            } else {
                Message msg = handler.obtainMessage();
                msg.what = taskid;
                msg.obj = result;
                handler.sendMessage(msg);
            }
            fileContent = DateUtils.toString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss")+" ---->[" + taskid + "]is result=" + fileContent + "\r\n";

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String get(String url, Map<String, String> params, int taskid) {
        return this.get(url,params, CommonHandler.getInstance().getHandler(),taskid);
    }

    @Override
    public String get(String url, Map<String, String> params, Handler handler, int taskid) {
        if(!checkNetWorking(C.mCurrentActivity,handler))return "";
        final String doUrl = url;
        final Map<String, String> doParams = params;
        final Handler _handler = handler;
        final int _taskid = taskid;
        final Runnable runnable = new Runnable() {
            public void run() {
                String result = HttpUtils.sendGet(doUrl, doParams);
                processResult(result,_taskid,_handler);
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName(UUID.randomUUID().toString());
        thread.start();
        ThreadPoolUtils.addMyThread(C.mCurrentActivity,thread);
        return thread.getName();
    }

    @Override
    public String post(String url, Map<String, String> params,int taskid) {
        return this.post(url,params, CommonHandler.getInstance().getHandler(), taskid,C.mCurrentActivity);
    }

    @Override
    public String post(String url, Map<String, String> params, Handler handler, int taskid) {
        return this.post(url,params,handler,taskid,C.mCurrentActivity);
    }

    @Override
    public String post(String url, Map<String, String> params, Handler handler, int taskid, Context context) {
        if(!checkNetWorking(context,handler))return "";
        final String doUrl = url;
        final Map<String, String> doParams = params;
        final Handler _handler = handler;
        final int _taskid = taskid;
        final Runnable runnable = new Runnable() {
            public void run() {
                String fileContent = DateUtils.toString(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss")+" ---->";
                fileContent += "taskid="+_taskid+"\r\n";
                if(!doParams.isEmpty())fileContent += "post params->"+doParams.toString()+"\r\n";
                String result = HttpUtils.sendPost(doUrl, doParams);
                processResult(result, _taskid,_handler);
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName(UUID.randomUUID().toString());
        thread.start();
        ThreadPoolUtils.addMyThread(C.mCurrentActivity,thread);
        return thread.getName();
    }

    @Override
    public String postImg(String url, String filePath, String fileName, Map<String, String> params, Handler handler,int taskid, Context context) {
        if(!checkNetWorking(context,handler))return "";
        final File file = new File(filePath);
        final String doUrl = url;
        final String doFileName = fileName;
        final Map<String, String> doParams = params;
        final Handler _handler = handler;
        final int _taskid = taskid;
        final Runnable runnable = new Runnable() {
            public void run() {
                String result = UploadUtils.getInstance().doUploadFile(file,doFileName,doUrl,doParams);
                processResult(result, _taskid,_handler);
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName(UUID.randomUUID().toString());
        thread.start();
        ThreadPoolUtils.addMyThread(C.mCurrentActivity,thread);
        return thread.getName();
    }

    @Override
    public String postImg(String url, String[] filePath, String fileName, Map<String, String> params, Handler handler, int taskid, Context context) {
        if(!checkNetWorking(context,handler))return "";
        final String[] doFilePath = filePath;
        final String doUrl = url;
        final String doFileName = fileName;
        final Map<String, String> doParams = params;
        final Handler _handler = handler;
        final int _taskid = taskid;
        final Runnable runnable = new Runnable() {
            public void run() {
                String result = UploadUtils.getInstance().doUploadFile(doFilePath,doFileName,doUrl,doParams);
                processResult(result, _taskid,_handler);
            }
        };
        Thread thread = new Thread(runnable);
        thread.setName(UUID.randomUUID().toString());
        thread.start();
        ThreadPoolUtils.addMyThread(C.mCurrentActivity,thread);
        return thread.getName();
    }
}
