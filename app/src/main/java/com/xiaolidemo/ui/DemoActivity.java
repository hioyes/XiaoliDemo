package com.xiaolidemo.ui;

import android.os.Environment;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.utils.FileUtils;
import com.xiaoli.library.utils.GsonUtils;
import com.xiaoli.library.utils.HttpUtils;
import com.xiaoli.library.utils.PromptUtils;
import com.xiaoli.library.utils.ZipUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.model.Branch;
import com.xiaolidemo.model.Test;
import com.xiaolidemo.services.BranchService;
import com.xiaolidemo.services.JMOptionService;
import com.xiaolidemo.services.TestService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * demo
 *
 * @author xiaokx on 2016-5-24 18:10
 * @Email:hioyes@qq.com
 */
public class DemoActivity extends BaseActivity {

    private ArrayList<String> mArrayList = new ArrayList<String>();

    private TextView mTvDemo;

    @Override
    protected int getLayoutResId() {
        return R.layout.demo;
    }

    @Override
    protected void initView() {
        mTvDemo = (TextView)findViewById(R.id.mTvDemo);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

        Log.e(TAG,Environment.getExternalStorageDirectory().toString());

        writeDb();

//        JMOptionService.getInstance(this).list(mHandler,12335);


        new Thread(new Runnable() {
            @Override
            public void run() {
                testUploadImg();
            }
        }).start();


    }

    /**
     * 测试图片提交
     */
    private void  testUploadImg(){
        String imgUrl = "/storage/emulated/0/lebrowser/homeicon/1639853567_342188932.jpg";
        imgUrl = "/storage/emulated/0/DCIM/Camera/IMG_20160629_140029.jpg";
        String filename = imgUrl.substring(imgUrl.lastIndexOf("/")+1,imgUrl.length());
        System.out.println(filename);
        String url = "http://192.168.5.61:8018/api/Vehicle/UpLoadImg/123/2/1639853567_342188932.jpg/1";
        Map params = new HashMap();
        params.put("sessionKey","123");
        params.put("carId","2");
        params.put("filename","1639853567_342188932.jpg");
        params.put("photoTypeId","1");

        Map<String, File> files = new HashMap<>();
        files.put("fn",new File(imgUrl));
        String result = HttpUtils.postImg(url,params,files);
        Log.e(TAG,result==null?"上传图片返回空结果":"上传图片提交结果："+result);

    }

    private void testLetectDb(){
        Test test = new Test();
        test.setVin(System.currentTimeMillis()+"");
        TestService.getInstance(this).insert(test);
        List<Test> list = TestService.getInstance(this).list();
        String str = "";
        for (Test test1 : list){
            str += test1.getVin()+"\t\t\t"+test1.getId();
            str += "\r\n";
            TestService.getInstance(this).update(test1);
        }
        Message message = mHandler.obtainMessage();
        message.what = 12333;
        message.obj = str;
        mHandler.sendMessage(message);
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
        Message message = mHandler.obtainMessage();
        message.what = 12334;
        message.obj = str;
        mHandler.sendMessage(message);
    }

    /**
     * 下载静态库并解压
     */
    private void writeDb(){
        new Thread() {
            @Override
            public void run() {
                testLetectDb();
                testCarModelDb();
//                try {
//                    String fileUrl = "http://app.lubaocar.com/carmodel.zip";
//                    String saveDirectory = Environment.getExternalStorageDirectory()+"/db/";
//                    FileUtils.createDirectory(saveDirectory);
//                    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/"), fileUrl.length());
//                    //创建按一个URL实例
//                    URL url = new URL(fileUrl);
//                    //创建一个HttpURLConnection的链接对象
//                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
//                    //获取所下载文件的InputStream对象
//                    InputStream inputStream = httpConn.getInputStream();
//                    int length = httpConn.getContentLength();
//                    FileOutputStream fileOutputStream = null;
//                    if (inputStream != null) {
//                        File file = new File(saveDirectory, fileName);
//                        fileOutputStream = new FileOutputStream(file);
//
//                        byte[] buf = new byte[1024 * 128];
//                        int ch = -1;
//                        int count = 0;
//                        while ((ch = inputStream.read(buf)) != -1) {
//                            count += ch;
//                            fileOutputStream.write(buf, 0, ch);
//                            Message msg = new Message();
//                            msg.what = 1;
//                            msg.arg1 = (int) (count * 100 / length);
//                            if (length > 0) {
//                            }
//                        }
//
//                    }
//                    fileOutputStream.flush();
//                    if (fileOutputStream != null) {
//                        fileOutputStream.close();
//                    }
//                    try
//                    {
//                        //解压缩
//                        String upzipDirectory = Environment.getExternalStorageDirectory()+"/db2/";
//                        FileUtils.createDirectory(upzipDirectory);
//                        ZipUtils.UnZipFolder(saveDirectory+fileName,upzipDirectory);
//
//                        //压缩
//                        String zipDirectory = Environment.getExternalStorageDirectory()+"/db3/";
//                        FileUtils.createDirectory(zipDirectory);
//                        ZipUtils.ZipFolder(upzipDirectory,zipDirectory+"db4.zip");
//                        Log.e(TAG,"finish--===========================================>");
//                    }catch (Exception e){
//                        Log.e(TAG,"eeeeeeeeeeeeeeeeeeeeeeeee-->"+e.getMessage());
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }

        }.start();
    }

    @Override
    public void handleMessageImpl(Message msg) {
        super.handleMessageImpl(msg);
        switch (msg.what){
            case 12333:
//                PromptUtils.showToast(msg.obj.toString());
                mTvDemo.setText(mTvDemo.getText()+"\r\n"+msg.obj.toString());
                break;
            case 12334:
//                PromptUtils.showToast(msg.obj.toString());
                mTvDemo.setText(mTvDemo.getText()+"\r\n"+msg.obj.toString());
                break;
            case 12335:
                if(msg.obj!=null){
                    mTvDemo.setText(mTvDemo.getText()+"\r\n"+msg.obj.toString());
                }
                break;
        }
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
