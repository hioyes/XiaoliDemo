package com.xiaolidemo.ui;

import android.os.Environment;
import android.os.Message;
import android.util.Log;

import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.utils.FileUtils;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaoli.library.utils.ZipUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.model.Branch;
import com.xiaolidemo.model.Test;
import com.xiaolidemo.services.BranchService;
import com.xiaolidemo.services.TestService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * demo
 *
 * @author xiaokx on 2016-5-24 18:10
 * @Email:hioyes@qq.com
 */
public class DemoActivity extends BaseActivity {

    private ArrayList<String> mArrayList = new ArrayList<String>();

    @Override
    protected int getLayoutResId() {
        return R.layout.demo;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        Log.e(TAG,Environment.getExternalStorageDirectory().toString());

        writeDb();



    }

    private void testLetectDb(){
        Test test = new Test();
        test.setName(System.currentTimeMillis()+"");
        TestService.getInstance(this).insert(test);
        List<Test> list = TestService.getInstance(this).list();
        String str = "";
        for (Test test1 : list){
            str += test1.getName();
            str += "\r\n";
        }
        PromptUtils.showToast(str);
    }

    /**
     * 测试读取CarModel库
     */
    private void testCarModelDb(){
        List<Branch> list = BranchService.getInstance(this).list();
        String str = "";
        for (Branch branch : list){
            str += branch.getBranchName();
            str += "\r\n";
        }
        PromptUtils.showToast(str);
    }

    /**
     * 下载静态库并解压
     */
    private void writeDb(){
        new Thread() {
            @Override
            public void run() {
                try {
                    String fileUrl = "http://app.lubaocar.com/CarModel.zip";
                    String saveDirectory = Environment.getExternalStorageDirectory()+"/db/";
                    FileUtils.createDirectory(saveDirectory);
                    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"), fileUrl.length());
                    //创建按一个URL实例
                    URL url = new URL(fileUrl);
                    //创建一个HttpURLConnection的链接对象
                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                    //获取所下载文件的InputStream对象
                    InputStream inputStream = httpConn.getInputStream();
                    int length = httpConn.getContentLength();
                    FileOutputStream fileOutputStream = null;
                    if (inputStream != null) {
                        File file = new File(saveDirectory, fileName);
                        fileOutputStream = new FileOutputStream(file);

                        byte[] buf = new byte[1024 * 128];
                        int ch = -1;
                        int count = 0;
                        while ((ch = inputStream.read(buf)) != -1) {
                            count += ch;
                            fileOutputStream.write(buf, 0, ch);
                            Message msg = new Message();
                            msg.what = 1;
                            msg.arg1 = (int) (count * 100 / length);
                            if (length > 0) {
                            }
                        }

                    }
                    fileOutputStream.flush();
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    try
                    {
                        //解压缩
                        String upzipDirectory = Environment.getExternalStorageDirectory()+"/db2/";
                        FileUtils.createDirectory(upzipDirectory);
                        ZipUtils.UnZipFolder(saveDirectory+fileName,upzipDirectory);

                        //压缩
                        String zipDirectory = Environment.getExternalStorageDirectory()+"/db3/";
                        FileUtils.createDirectory(zipDirectory);
                        ZipUtils.ZipFolder(upzipDirectory,zipDirectory+"db4.zip");
                        testLetectDb();
                    }catch (Exception e){
                        Log.e(TAG,"eeeeeeeeeeeeeeeeeeeeeeeee-->"+e.getMessage());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }

    @Override
    public void handleMessageImpl(Message msg) {
        super.handleMessageImpl(msg);
    }

    private ArrayList<String> getData() {
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        mArrayList.add("测试数据1");
        mArrayList.add("测试数据2");
        mArrayList.add("测试数据3");
        mArrayList.add("测试数据4");
        mArrayList.add("测试数据5");
        mArrayList.add("测试数据6");
        return mArrayList;
    }

    private ArrayList<String> getData2() {
        mArrayList = new ArrayList<String>();
        mArrayList.add("中华魂测试数据1");
        mArrayList.add("中华魂测试数据2");
        mArrayList.add("中华魂测试数据3");
        mArrayList.add("中华魂测试数据4");
        mArrayList.add("中华魂测试数据5");
        mArrayList.add("中华魂测试数据6");
        mArrayList.add("中华魂测试数据1");
        mArrayList.add("中华魂测试数据2");
        mArrayList.add("中华魂测试数据3");
        mArrayList.add("中华魂测试数据4");
        mArrayList.add("中华魂测试数据5");
        mArrayList.add("中华魂测试数据6");
        mArrayList.add("中华魂测试数据1");
        mArrayList.add("中华魂测试数据2");
        mArrayList.add("中华魂测试数据3");
        mArrayList.add("中华魂测试数据4");
        mArrayList.add("中华魂测试数据5");
        mArrayList.add("中华魂测试数据6");
        return mArrayList;
    }
}
