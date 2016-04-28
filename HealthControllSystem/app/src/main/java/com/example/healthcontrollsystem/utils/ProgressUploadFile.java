package com.example.healthcontrollsystem.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public class ProgressUploadFile {
	private static final OkHttpClient okHttpClient = new OkHttpClient();

	private Context context;
	private File file;

	public ProgressUploadFile(Context context,File file) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.file = file;
	}

	public void upload(){
		run();
	}

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what==0) {
				ToastUtils.showToast((String)msg.obj, context);
				try {
					JSONObject jsonObject = new JSONObject((String)msg.obj);
					RSharePreference.putString("user_image", jsonObject.getString("path"), context);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				
			}
		};
	};

	private void run() {

		MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

		//		File file = new File("D:\\file.jpg");
		builder.addFormDataPart("file", file.getName(), createCustomRequestBody(MultipartBuilder.FORM, file, new ProgressListener() {
			@Override 
			public void onProgress(long totalBytes, long remainingBytes, boolean done) {
//				System.out.print((totalBytes - remainingBytes) * 100 / totalBytes + "%");
//				Log.d("imageloading", (totalBytes - remainingBytes) * 100 / totalBytes + "%");
			}
		}));

		RequestBody requestBody = builder.build();

		Request request = new Request.Builder()
		.url(AppConfig.BASE_URL+AppConfig.UPLOAD_IMAGE) //地址
		.post(requestBody)
		.build();

		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override 
			public void onFailure(Request request, IOException e) {

			}
			
			

			@Override 
			public void onResponse(Response response) throws IOException {
//				System.out.println("response.body().string() = " + response.body().string());
				Log.d("imageloading11111", response.body().toString());
				Message msg = new Message();
				msg.what = 0;
				String result = response.body().string();
				String responseString = result.substring(result.indexOf("{"), result.indexOf("}")+1);
				msg.obj = responseString;
				handler.sendMessage(msg);
			}
		});
	}

	public static RequestBody createCustomRequestBody(final MediaType contentType, final File file, final ProgressListener listener) {
		return new RequestBody() {
			@Override 
			public MediaType contentType() {
				return contentType;
			}

			@Override 
			public long contentLength() {
				return file.length();
			}

			@Override 
			public void writeTo(BufferedSink sink) throws IOException {
				Source source;
				try {
					source = Okio.source(file);
					//sink.writeAll(source);
					Buffer buf = new Buffer();
					Long remaining = contentLength();
					for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
						sink.write(buf, readCount);
						listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	interface ProgressListener {
		void onProgress(long totalBytes, long remainingBytes, boolean done);
	}


}