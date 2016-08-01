package com.xiaoli.library.camera;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.xiaoli.library.C;
import com.xiaoli.library.R;
import com.xiaoli.library.utils.DateUtils;
import com.xiaoli.library.utils.FileUtils;

/**
 * Activity中使用自定义相机
 * xiaokx
 * hioyes@qq.com
 * 2016-7-29
 */
public class CameraActivity extends Activity implements CameraPreview.OnCameraStatusListener{

    private String TAG = "CameraActivity";
    private CameraPreview mCameraPreview;//自定义相机
    private ImageView mIvPhotograph;//拍照按钮
    private FocusView mFocusView;//聚焦View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_camera);
        mCameraPreview = (CameraPreview)findViewById(R.id.mCameraPreview);
        mCameraPreview.setRate(1.33f);//比率设置
//        mCameraPreview.setFlashMode(Camera.Parameters.FLASH_MODE_ON);//闪光灯设置
        mIvPhotograph = (ImageView)findViewById(R.id.mIvPhotograph);
        mFocusView = (FocusView)findViewById(R.id.mFocusView);
        mCameraPreview.setOnCameraStatusListener(this);
        mCameraPreview.setFocusView(mFocusView);

    }


    /**
     * 执行拍照
     * @param view
     */
    public void doPhotograph(View view){
        if(mCameraPreview != null) {
            mCameraPreview.takePicture();
        }
    }

    @Override
    public void onCameraStopped(byte[] data) {
        Log.e(TAG, "in...onCameraStopped");
        // 创建图像
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        // 图像名称
        long dateTaken = System.currentTimeMillis();
        String filename = DateUtils.toString(dateTaken,"yyyy-MM-dd-hh-mm-ss") + ".jpg";
        // 存储图像（PATH目录）
        Uri source = FileUtils.saveImage(getContentResolver(), filename, dateTaken, C.ROOT_CATALOG+"XiaoliMedia/",
                filename, bitmap, data);
        Log.e(TAG,"photo path is ->"+FileUtils.getRealFilePath(getApplicationContext(),source));
    }
}
