package com.xiaoli.library.net;

import android.content.Context;
import android.os.Handler;

import com.xiaoli.library.net.impl.HttpConnection;

import java.util.Map;

/**
 * @ClassName: HttpWrapper
 * @author xiaokx Email:hioyes@qq.com
 * @date 2014-11-6 下午8:17:55
 * @Description:http请求装饰者
 */
public class HttpWrapper implements INetwork {
	private static HttpWrapper httpWrapper = null;
	private static INetwork  netwrok;

	public HttpWrapper getInstance() {
		if(httpWrapper==null){
			netwrok = new HttpConnection();
			httpWrapper = new HttpWrapper();
		}
		return httpWrapper;
	}

	@Override
	public boolean checkNetWorking(Context context, Handler handler) {
		return netwrok.checkNetWorking(context,handler);
	}

	@Override
	public void processResult(String result, int taskid, Handler handler) {
		netwrok.processResult(result,taskid,handler);
	}

	@Override
	public String get(String url, Map<String, String> params, int taskid) {
		return netwrok.get(url,params,taskid);
	}

	@Override
	public String get(String url, Map<String, String> params, Handler handler, int taskid) {
		return netwrok.get(url,params, handler,taskid);
	}

	@Override
	public String post(String url, Map<String, String> params,int taskid) {
		return netwrok.post(url,params,taskid);
	}

	@Override
	public String post(String url, Map<String, String> params, Handler handler, int taskid) {
		return netwrok.post(url,params,handler,taskid);
	}

	@Override
	public String post(String url, Map<String, String> params, Handler handler, int taskid, Context context) {
		return netwrok.post(url,params,handler,taskid,context);
	}

	@Override
	public String postImg(String url, String filePath, String fileName, Map<String, String> params, Handler handler, int taskid, Context context) {
		return netwrok.postImg(url,filePath,fileName,params,handler,taskid,context);
	}

	@Override
	public String postImg(String url, String[] filePath, String fileName, Map<String, String> params, Handler handler, int taskid, Context context) {
		return netwrok.postImg(url,filePath,fileName,params,handler,taskid,context);
	}
}
