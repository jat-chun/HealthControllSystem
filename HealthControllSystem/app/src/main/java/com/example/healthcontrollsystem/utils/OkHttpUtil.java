package com.example.healthcontrollsystem.utils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * 网络请求
 * @author jat
 *
 */
public class OkHttpUtil {

	private static final OkHttpClient mOkHttpClient = new OkHttpClient();
	//编码格式
	private static final String CHARSET_NAME = "utf-8";
	static{
		mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
	}

	/**
	 * 通过传入URL和键值对列表，返回请求
	 * @param url
	 * @param params
     * @return
     */
	public static Request requestPostJson(String url, List<BasicNameValuePair> params){
		FormEncodingBuilder formBody = new FormEncodingBuilder();
		for (int i = 0; i < params.size(); i++) {
			formBody.add(params.get(i).getName(), params.get(i).getValue());
		}
		RequestBody requestBody = formBody.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
		return request;
	}

	/**
	 * 通过传入URL和键值对列表，返回请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static Request requestPost(String url,List<BasicNameValuePair> params){
		FormEncodingBuilder formBody = new FormEncodingBuilder();
		for (int i = 0; i < params.size(); i++) {
			formBody.add(params.get(i).getName(), params.get(i).getValue());
		}
		RequestBody requestBody = formBody.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
		return request;
	}

	/**
	 * 通过传入URL和参数键值对列表，返回请求
	 * @param url
	 * @param params
     * @return
     */
	public static Request requestGet(String url,List<BasicNameValuePair> params){
		String getUrl = url+"?"+URLEncodedUtils.format(params, CHARSET_NAME);
		Request request = new Request.Builder().url(getUrl).build();
		return request;
	}

	/**
	 * 该不会开启异步线程
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static Response execute(Request request) throws IOException{
		return mOkHttpClient.newCall(request).execute();
	}

	/**
	 * 开启一部线程访问网络,并设置监听
	 * @param request
	 * @param responseCallback
	 */
	public static void enqueue(Request request,Callback responseCallback){
		mOkHttpClient.newCall(request).enqueue(responseCallback);
	}
	
	
	/**
	 * 开启一部线程访问网络，且不在意返回结果（实现空callback
	 * @param request
	 */
	public static void enqueue(Request request){
		mOkHttpClient.newCall(request).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

	
	/**
	 *
	 * 不开启异步线程，返回响应内容
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromServer(String url) throws IOException{
		Request request = new Request.Builder().url(url).build();
		Response response = execute(request);
		if (response.isSuccessful()) {
			String responseUrl = response.body().string();
			return responseUrl;
		}else {
			throw new IOException("Unexpected code "+response);
		}
	}


	/**
	 * 不开启异步post方式提交，返回请求内容
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception
     */
	public static String getStringFromServerWithParams(String url,List<BasicNameValuePair> params) throws Exception{
		FormEncodingBuilder formBody = new FormEncodingBuilder();
		for (int i = 0; i < params.size(); i++) {
			formBody.add(params.get(i).getName(), params.get(i).getValue());
		}
		RequestBody requestBody = formBody.build();
		Request request = new Request.Builder().url(url).post(requestBody).build();
		Response response = execute(request);
		if (response.isSuccessful()) {
			String responseUrl = response.body().string();
			return responseUrl;
		}else {
			throw new IOException("Unexpected code "+response);
		}
	}
	
	/**
	 * 这里使用了HttpClientde API.只是为了方便
	 * @param params
	 * @return
	 */
	public static String formatParams(List<BasicNameValuePair> params){
		return URLEncodedUtils.format(params, CHARSET_NAME);
	}
	/**
	 * 为HttpGet的url方便的添加多个name value参数
	 * @param url
	 * @param params
	 * @return
	 */
	public static String attachHttpGetParams(String url,List<BasicNameValuePair> params){
		return url+"?"+formatParams(params);
	}
	/**
	 * 为httpget的URL方便的添加一个namevalue的参数
	 * @param url
	 * @param name
	 * @param value
	 * @return
	 */
	public static String attachHttpGetParam(String url,String name,String value){
		return url+"?" +name+"="+value;
	}
}
