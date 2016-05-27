package com.xiaoli.library.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaokx Email:hioyes@qq.com
 * @ClassName: HttpUtils
 * @date 2014-11-6 下午8:17:55
 * @Description:http网络请求工具类
 */
public class HttpUtils {

    private static final int readTimeOut = 10 * 1000; // 读取超时
    private static final int connectTimeout = 10 * 1000; // 超时时间

    /**
     * 网络请求超时
     */
    public static final String TIME_OUT = "TIME_OUT";

    /**
     * url地址错误
     */
    public static final String FILE_NOT_FOUND = "FILE_NOT_FOUND";

    /**
     * 服务器找不到
     */
    public static final String SERVER_NOT_FOUND = "SERVER_NOT_FOUND";

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url    发送请求的URL
     * @param params 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String, String> params) {
        return sendGet(url, params, 0);
    }

    /**
     * 指定URL发送GET方法的请求
     *
     * @param url
     * @param params
     * @param timeout
     * @return
     */
    public static String sendGet(String url, Map<String, String> params, int timeout) {
        String param = paramsConvertString(params);
        Log.e("apiGetUrl->", url);
        Log.e("apiGetParams->", param);
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            if (!"".equals(param)) urlNameString += "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            if (timeout > 0) {
                connection.setReadTimeout(timeout * 1000);
                connection.setConnectTimeout(timeout * 1000);
            } else {
                connection.setReadTimeout(readTimeOut);
                connection.setConnectTimeout(connectTimeout);
            }
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }
        catch (UnknownHostException e){
            System.out.println("未知主机" + e);
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url    发送请求的 URL
     * @param params 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, String> params) {
        return sendPost(url, params, 0);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     * @param params
     * @param timeout
     * @return
     */
    public static String sendPost(String url, Map<String, String> params, int timeout) {
        String param = paramsConvertString(params);
        Log.e("apiPostUrl->", url);
        Log.e("apiPostParams->", param);
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            if (timeout > 0) {
                conn.setReadTimeout(timeout * 1000);//设置连接主机超时（单位：毫秒）
                conn.setConnectTimeout(timeout * 1000);//设置从主机读取数据超时（单位：毫秒）
            } else {
                conn.setReadTimeout(readTimeOut);
                conn.setConnectTimeout(connectTimeout);
            }
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            if(conn.getOutputStream()==null){
                result = FILE_NOT_FOUND;
                return result;
            }
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            if (!"".equals(param)) out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (FileNotFoundException e) {
            result = FILE_NOT_FOUND;
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            result = TIME_OUT;
            e.printStackTrace();
        }catch (SocketException e){
            result = SERVER_NOT_FOUND;
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将Map中的参数转成字符串形式
     *
     * @param params
     * @return
     */
    public static String paramsConvertString(Map<String, String> params) {
        if (params == null) return "";
        String paramStr = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String val = entry.getValue();
            if (val.contains("###")) {//判断是否为参数数组
                String[] arr = val.split("###");
                val = "";
                for (String str : arr) {
                    val = str;
                    try {
                        val = URLEncoder.encode(val, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    paramStr += "&";
                    paramStr += entry.getKey() + "=" + val;
                }
            } else {
                try {
                    val = URLEncoder.encode(val, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                paramStr += "&";
                paramStr += entry.getKey() + "=" + val;
            }

        }
        return paramStr.replaceFirst("&", "");
    }


    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("name", "张三");
        String str = HttpUtils.paramsConvertString(params);
        System.out.println(str);
    }

}
