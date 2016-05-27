package com.xiaoli.library.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.WindowManager;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName:
 * @date 2015-11-5
 * @Description: 文件工具类
 */
public class FileUtils {

    /**
     * @param context
     * @param uri
     * @return 获取图片的真是路径
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * 本地(SD卡)图片转换成Bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getDiskBitmap(String filePath)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(filePath);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(filePath);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * 根据sd卡路径得到压缩图片
     * @param activity
     * @param filePath
     * @return Bitmap
     */
    public static Bitmap getSmallBitmap(Activity activity,String filePath){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        WindowManager wm = activity.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        if(bitmap == null){
            return  null;
        }
        return bitmap;
    }

    /**
     * 根据sd卡路径得到压缩图片
     * @param filePath
     * @return File
     */
    public static File getSmallFile(Activity context,String filePath) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        WindowManager wm = context.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, width, height);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(filePath, options);
        if(bm == null){
            return  null;
        }
        int degree = readPictureDegree(filePath);
        bm = rotateBitmap(bm,degree) ;
        BufferedOutputStream bos = null ;
        //创建一个File
        String path = filePath.substring(filePath.lastIndexOf("/")+1);
        String address = context.getFilesDir().getPath()+path+".jpg";
        File file = new File(address);
        try{
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 30, bos);

        } catch(Exception e){
        }finally{
            try {
                if(bos != null)
                    bos.close() ;
            } catch (IOException e) {
            }
        }
        return file;
    }


    /**
     * 处理图片旋转
     */
    private static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
        }
        return degree;
    }
    /**
     * 图片旋转
     * @param bitmap
     * @param rotate
     * @return
     */
    private static Bitmap rotateBitmap(Bitmap bitmap, int rotate){
        if(bitmap == null)
            return null ;

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // Setting post rotate to 90
        Matrix mtx = new Matrix();
        mtx.postRotate(rotate);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
    }

    /**
     * 在同比例计算
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }

        return inSampleSize;
    }

    /**
     * 将资源文件复制到sd卡
     * @param targetFileName
     * @param targetPath
     * @param sourceId
     * @param context
     */
    public static void copyDdFile(String targetFileName, String targetPath, int sourceId,Context context) {
        try {
            File dir = new File(targetPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (!(new File(targetPath+targetFileName)).exists()) {
                InputStream is = context.getResources().openRawResource(sourceId);
                FileOutputStream fos = new FileOutputStream(targetPath+targetFileName);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把内容写入文件
     *
     * @param filePath
     * @param fileContent
     */
    public static void write(String filePath, String fileContent) {

        try {
            FileOutputStream fo = new FileOutputStream(filePath);
            OutputStreamWriter out = new OutputStreamWriter(fo, "UTF-8");

            out.write(fileContent);

            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 重置文件名称，避免文件过大
     * @param directory 文件目录 E:\\test\\
     * @param fileName 文件名称 user.txt
     * @param fileSize 每个文件存在字节大小
     * @return
     */
    public static String resetFileName(String directory,String fileName,int fileSize){
        File file = new File(directory+fileName);
        if((file.length()/1024)<fileSize)return fileName;
        for (int i=0;i<10000;i++){
            String ext = "_"+i+".";
            String fn = fileName.replace(".",ext);
            file = new File(directory+fn);
            if((file.length()/1024)<fileSize)return fn;
        }
        return fileName;
    }

    /**
     * 把内容以UTF-8编码叠加写入文件
     * @param directory E:\\test\\
     * @param fileName user.txt
     * @param fileContent 测试
     */
    public static void writeAppend(String directory,String fileName, String fileContent) {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        fileName = resetFileName(directory,fileName,1024);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(directory+fileName,true);
            Writer out = new OutputStreamWriter(fos, "UTF-8");
            BufferedWriter buf = new BufferedWriter(out);
            buf.append(fileContent);
            buf.newLine();
            buf.close();
            out.close();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 按指定大小压缩图片
     * @param bitmap
     * @param width
     * @param height
     * @param fileSize 文件压缩大小(kb)
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap, int width, int height,int fileSize){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        newbmp = compressImage(newbmp,fileSize);
        bitmap.recycle();
        matrix = null;
        return newbmp;
    }

    /**
     * 图片压缩:质量压缩方法
     * @param image
     * @param fileSize 文件压缩大小(kb)
     * @return
     */
    public static Bitmap compressImage(Bitmap image,int fileSize) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            int options = 100;
            while ( baos.toByteArray().length / 1024>fileSize) {	//循环判断如果压缩后图片是否大于目标大小,大于继续压缩
                baos.reset();//重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
                options -= 10;//每次都减少10
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

            baos.close();
            isBm.close();
            image.recycle();
            return bitmap;
        } catch (IOException e) {
            // TODO Auto-generated catch blockO
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 删除日志,只保留7天的日志
     * @param file
     */
    public static void deleteLog(File file){
        try{
            if(!file.exists())return;
            if (file.isFile()) {
                file.delete();
                return;
            }

            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                if (childFiles == null || childFiles.length == 0) {
                    return;
                }
                for (int i = 0; i < childFiles.length; i++) {
                    File currFile = childFiles[i];
                    String fileName = currFile.getName();
                    fileName = fileName.replaceAll("[a-zA-Z]","");
                    if(fileName.length()>=10){
                        fileName = fileName.substring(0,10);
                        String eL = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
                        Pattern p = Pattern.compile(eL);
                        Matcher m = p.matcher(fileName);
                        boolean dateFlag = m.matches();
                        if(!dateFlag){
                            //不是yyyy-MM-dd格式
                            currFile.delete();
                            continue;
                        }
                    }else{
                        currFile.delete();
                        continue;
                    }
                    String sevenDay = DateUtils.getBeforDate(7,"yyyy-MM-dd");
                    long lastDay = DateUtils.getDateline(sevenDay);
                    long fileDay = DateUtils.getDateline(fileName);
                    if(lastDay>fileDay){
                        currFile.delete();
                    }

                }
            }
        }catch (Exception e){}

    }
}
