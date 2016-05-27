package com.xiaoli.library.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName:
 * @date 2015-11-5
 * @Description:支持上传文件和参数
 */
public class UploadUtils {
    private static UploadUtils uploadUtil;
    private static final String BOUNDARY =  UUID.randomUUID().toString(); // 边界标识 随机生成
    private static final String PREFIX = "--";
    private static final String LINE_END = "\r\n";
    private static final String CONTENT_TYPE = "multipart/form-data"; // 内容类型
    private int readTimeOut = 10 * 1000; // 读取超时
    private int connectTimeout = 10 * 1000; // 超时时间
    /***
     * 请求使用多长时间
     */
    private static int requestTime = 0;
    private static final String CHARSET = "utf-8"; // 设置编码

    /***
     * 上传成功
     */
    public static final int UPLOAD_SUCCESS_CODE = 1;
    /**
     * 文件不存在
     */
    public static final int UPLOAD_FILE_NOT_EXISTS_CODE = 2;
    /**
     * 服务器出错
     */
    public static final int UPLOAD_SERVER_ERROR_CODE = 3;
    protected static final int WHAT_TO_UPLOAD = 1;
    protected static final int WHAT_UPLOAD_DONE = 2;

    /**
     * 单例模式获取上传工具类
     * @return
     */
    public static UploadUtils getInstance() {
        if (null == uploadUtil) {
            uploadUtil = new UploadUtils();
        }
        return uploadUtil;
    }
    /**
     * 文件上传
     * @param file
     * @param fileKey
     * @param requestURL
     * @param param
     */
    public String doUploadFile(File file, String fileKey, String requestURL,Map<String, String> param) {
        return this.doUploadFile(file,fileKey,requestURL,param,0);
    }

    /**
     * 文件上传
     * @param uploadFiles
     * @param fileKey
     * @param requestURL
     * @param param
     */
    public String doUploadFile(String[] uploadFiles, String fileKey, String requestURL,Map<String, String> param) {
        return this.doUploadFile(uploadFiles,fileKey,requestURL,param,0);
    }


    /**
     * 文件上传
     * @param file
     * @param fileKey
     * @param RequestURL
     * @param param
     * @param timeout
     * @return超时时间
     */
    public String doUploadFile(File file, String fileKey, String RequestURL,Map<String, String> param,int timeout) {
        String result = null;
        requestTime= 0;
        long requestTime = System.currentTimeMillis();
        long responseTime = 0;
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if(timeout>0){
                conn.setReadTimeout(timeout*1000);
                conn.setConnectTimeout(timeout*1000);
            }else{
                conn.setReadTimeout(readTimeOut);
                conn.setConnectTimeout(connectTimeout);
            }
            conn.setDoInput(true); // 允许输入流
            conn.setDoOutput(true); // 允许输出流
            conn.setUseCaches(false); // 不允许使用缓存
            conn.setRequestMethod("POST"); // 请求方式
            conn.setRequestProperty("Charset", CHARSET); // 设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            /**
             * 当文件不为空，把文件包装并且上传
             */
            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            StringBuffer sb = null;
            String params = "";
            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                    sb.append(value).append(LINE_END);
                    params = sb.toString();
                    dos.write(params.getBytes());
                }
            }
            sb = new StringBuffer();
            /**
             * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
             * filename是文件的名字，包含后缀名的 比如:abc.png
             */
            sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
            sb.append("Content-Disposition:form-data; name=\"" + fileKey
                    + "\"; filename=\"" + file.getName() + "\"" + LINE_END);
            sb.append("Content-Type:image/pjpeg" + LINE_END); // 这里配置的Content-type很重要的 ，用于服务器端辨别文件的类型的
            sb.append(LINE_END);
            params = sb.toString();

            dos.write(params.getBytes());
            /**上传文件*/
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int len = 0;
            int curLen = 0;
            while ((len = is.read(bytes)) != -1) {
                curLen += len;
                dos.write(bytes, 0, len);
            }
            is.close();

            dos.write(LINE_END.getBytes());
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
            dos.write(end_data);
            dos.flush();
            /**
             * 获取响应码 200=成功 当响应成功，获取响应的流
             */
            int res = conn.getResponseCode();
            responseTime = System.currentTimeMillis();
            this.requestTime = (int) ((responseTime-requestTime)/1000);
            if (res == 200) {
                InputStream input = conn.getInputStream();
                StringBuffer sb1 = new StringBuffer();
                int ss;
                while ((ss = input.read()) != -1) {
                    sb1.append((char) ss);
                }
                result = sb1.toString();

                return result;
            } else {

                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }catch(SocketTimeoutException e){
            return HttpUtils.TIME_OUT;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 文件上传（多个）
     * @param uploadFiles
     * @param fileKey
     * @param RequestURL
     * @param param
     * @param timeout
     * @return
     */
    public String doUploadFile(String[] uploadFiles, String fileKey, String RequestURL,Map<String, String> param,int timeout) {
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            if(timeout>0){
                con.setReadTimeout(timeout*1000);
                con.setConnectTimeout(timeout*1000);
            }else{
                con.setReadTimeout(readTimeOut);
                con.setConnectTimeout(connectTimeout);
            }
            con.setDoInput(true); // 允许输入流
            con.setDoOutput(true); // 允许输出流
            con.setUseCaches(false); // 不允许使用缓存
            con.setRequestMethod("POST"); // 请求方式
            con.setRequestProperty("Charset", CHARSET); // 设置编码
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            con.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            DataOutputStream ds =  new DataOutputStream(con.getOutputStream());
            StringBuffer sb = null;
            String params = "";
            /***
             * 以下是用于上传参数
             */
            if (param != null && param.size() > 0) {
                Iterator<String> it = param.keySet().iterator();
                while (it.hasNext()) {
                    sb = new StringBuffer();
                    String key = it.next();
                    String value = param.get(key);
                    sb.append(PREFIX).append(BOUNDARY).append(LINE_END);
                    sb.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(LINE_END).append(LINE_END);
                    sb.append(value).append(LINE_END);
                    params = sb.toString();
                    ds.write(params.getBytes());
                }
            }
            if(uploadFiles!=null && uploadFiles.length>0) {
                for (int i = 0; i < uploadFiles.length; i++) {
                    String uploadFile = uploadFiles[i];
                    String filename = uploadFile.substring(uploadFile.lastIndexOf("//") + 1);
                    ds.writeBytes(PREFIX + BOUNDARY + LINE_END);
                    ds.writeBytes("Content-Disposition: form-data; " + "name=file" + i + ";filename=" + filename + LINE_END);
                    ds.writeBytes("Content-Type: application/octet-stream; charset="+ CHARSET + LINE_END);
                    ds.writeBytes(LINE_END);
                    FileInputStream fStream = new FileInputStream(uploadFile);
                    int bufferSize = 1024;
                    byte[] buffer = new byte[bufferSize];
                    int length = -1;
                    while ((length = fStream.read(buffer)) != -1) {
                        ds.write(buffer, 0, length);
                    }
                    ds.writeBytes(LINE_END);
                    fStream.close();
                }
                ds.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
                ds.flush();
            }

            // 得到响应码
            StringBuilder sb2 = new StringBuilder();
            int res = con.getResponseCode();
            InputStream in = null;
            if (res == 200) {
                in = con.getInputStream();
                int ch;

                while ((ch = in.read()) != -1) {
                    sb2.append((char) ch);
                }
            }
            ds.close();
            con.disconnect();

//            // 定义BufferedReader输入流来读取URL的响应
//            InputStream is = con.getInputStream();
//            int ch;
//            StringBuffer b = new StringBuffer();
//            while ((ch = is.read()) != -1) {
//                b.append((char) ch);
//            }
//            String s = b.toString();
//            ds.close();
            return sb2.toString();
        }catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }catch(SocketTimeoutException e){
            return HttpUtils.TIME_OUT;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
