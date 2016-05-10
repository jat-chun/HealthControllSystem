package com.example.healthcontrollsystem.activity;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.example.healthcontrollsystem.R;
import com.example.healthcontrollsystem.service.UserInformationService;
import com.example.healthcontrollsystem.utils.AppConfig;
import com.example.healthcontrollsystem.utils.CustomDialog;
import com.example.healthcontrollsystem.utils.DateComparator;
import com.example.healthcontrollsystem.utils.OkHttpUtil;
import com.example.healthcontrollsystem.utils.ProgressUploadFile;
import com.example.healthcontrollsystem.utils.ToastUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tandong.sa.activity.SmartActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * 注册
 * @author jat
 *
 */
public class RegisterActivity extends SmartActivity implements OnClickListener{

	//相机回调
	private static int CAMERA_REQUEST_CODE = 1;
	//图库回调
	private static int GALLERY_REQUEST_CODE = 2;
	//关闭请求
	private static int CROP_REQUEST_CODE = 3;

	private String sex = "男";
	//返回键
	private ImageView head_back;

	//头像
	private BootstrapCircleThumbnail civ_register_image;
	//用户名
	private EditText et_register_username;
	//用户名提示
	private TextView tv_register_username;
	//密码
	private EditText et_register_password;
	//密码提示
	private TextView tv_register_password;
	//重复密码
	private EditText et_register_password_again;
	//重复密码提示
	private TextView tv_register_password_again;
	//电话
	private EditText et_register_phone;
	//性别
	private RadioGroup rg_register_sex;
	//体重
	private EditText et_register_weight;
	//男
	private RadioButton rb_register_man;
	//女
	private RadioButton rb_register_woman;
	//年龄
	private EditText et_register_age;
	//头部保存
	private TextView head_save;
	//电话提示
	private TextView tv_register_phone;
	//注册
	private Button bt_register_submit;

	//电话
	private boolean isPhone = false;

	//密码
	private boolean isPsw = false;

	//用户名
	private boolean isName = false;

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	OkHttpClient client = new OkHttpClient();

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			if (msg.what==1) {
				String result = (String) msg.obj;
				try {
					JSONObject object = new JSONObject(result);
					if (object.getBoolean("result")) {
						tv_register_username.setText("用户名可用");
						tv_register_username.setTextColor(Color.GREEN);
						isName = true;
					}else {
						tv_register_username.setText(R.string.user_name_used);
						tv_register_username.setTextColor(Color.RED);
						isName = false;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if(msg.what==2){
				JSONObject jsonObject = null;
				try {
					jsonObject = new JSONObject((String)msg.obj);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					if (jsonObject.get("result").equals("success")) {
						savePreferenceString("user_name", et_register_username.getText().toString());
						Intent intent = new Intent(RegisterActivity.this, UserInformationService.class);
						RegisterActivity.this.startService(intent);
						
						Intent intent2 = new Intent(RegisterActivity.this, HomesActivity.class);
						RegisterActivity.this.startActivity(intent2);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);

		initView();
//		initMotion();

	}
	/**
	 * 初始化
	 */
	private void initView(){
		head_back = (ImageView) findViewById(R.id.head_back);
		civ_register_image = (BootstrapCircleThumbnail) findViewById(R.id.civ_register_image);
		et_register_password = (EditText) findViewById(R.id.et_register_password);
		et_register_username = (EditText) findViewById(R.id.et_register_username);
		et_register_password_again = (EditText) findViewById(R.id.et_register_password_again);
		et_register_phone = (EditText) findViewById(R.id.et_register_phone);
		tv_register_password = (TextView) findViewById(R.id.tv_register_password);
		tv_register_password_again = (TextView) findViewById(R.id.tv_register_password_again);
		tv_register_username = (TextView) findViewById(R.id.tv_register_username);
		tv_register_phone = (TextView) findViewById(R.id.tv_register_phone);
		et_register_weight = (EditText)findViewById(R.id.et_register_weight);
		rg_register_sex = (RadioGroup) findViewById(R.id.rg_register_sex);
		rb_register_man = (RadioButton) findViewById(R.id.rb_register_man);
		rb_register_woman = (RadioButton) findViewById(R.id.rb_register_woman);
		et_register_age = (EditText) findViewById(R.id.et_register_age);
		head_save = (TextView) findViewById(R.id.head_save);
		bt_register_submit = (Button) findViewById(R.id.bt_register_submit);
		bt_register_submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				final CustomDialog dialog = new CustomDialog(RegisterActivity.this,false);
				dialog.setShowingMsg("注册成功");
				dialog.show();
				dialog.setOnSingleClickListener(new CustomDialog.OnSingleClickListener() {
					@Override
					public void onClick() {
						dialog.dismiss();
					}
				});
			}
		});
	}

	/**
	 * 初始化动作
	 */
	private void initMotion(){
		head_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		head_save.setText("");
		et_register_phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String phone_num = et_register_phone.getText().toString();
				if (!DateComparator.isMobile(phone_num)) {
					tv_register_phone.setText(R.string.no_phone);
				}else {
					tv_register_phone.setText("");
					isPhone = true;
				}
			}
		});

		//更换头像
		civ_register_image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showSelect();
			}
		});
		//用户名监听
		et_register_username.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (et_register_username.getText().toString().equals("")) {
				}else {
					try {
						confirmName(et_register_username.getText().toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		//密码监听
		et_register_password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				if (et_register_password.getText().toString().equals("")) {
					tv_register_password.setText(R.string.edit_passwod);
				}else if(DateComparator.passwordFormat(et_register_password.getText().toString())){
					tv_register_password.setText("");
				}else {
					tv_register_password.setText("密码为6-12位");
					isPsw=false;
				}
			}
		});
		//密码不一致
		et_register_password_again.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				//提示密码重复
				if (et_register_password.getText().toString().equals(et_register_password_again.getText().toString())
						&&DateComparator.passwordFormat(et_register_password_again.getText().toString())) {
					tv_register_password_again.setText("");
					isPsw = true;
				}else {
					tv_register_password_again.setText(R.string.password_no_same);
				}
			}
		});

		rb_register_man.setChecked(true);
		//设置性别监听
		rg_register_sex.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				if (arg1 == rb_register_man.getId()) {
					sex = "男";
				}else {
					sex = "女";
				}
			}
		});
		bt_register_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// 读取一个key值方法
				String user_image = getPreferenceString("user_image");
				if (user_image==null) {
					user_image = new String("http://localhost:8080/HealthServer/image/1458645324124.jpg");
				}
				if (isName&&isPhone&&isPsw) {
					String user_name = et_register_username.getText().toString();
					String user_password = et_register_password.getText().toString();
					String user_phone = et_register_phone.getText().toString();
					String user_age = et_register_age.getText().toString();

				}
			}
		});
	}


	//显示弹框
	private void showSelect(){
		final AlertDialog mAlertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
		mAlertDialog.show();
		mAlertDialog.setCancelable(true);
		View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.select_phone, null);
		mAlertDialog.setContentView(view);
		mAlertDialog.getWindow().setGravity(Gravity.BOTTOM);

		// 获取屏幕参数
		WindowManager.LayoutParams lp=mAlertDialog.getWindow().getAttributes();
		lp.dimAmount=0.2f;

		// 获取屏幕宽高
		lp.width=(RegisterActivity.this).getWindowManager().getDefaultDisplay().getWidth();
		lp.height=LayoutParams.WRAP_CONTENT;
		mAlertDialog.getWindow().setAttributes(lp);
		mAlertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

		TextView tv_camera = (TextView) view.findViewById(R.id.tv_camera);
		TextView tv_gallery = (TextView) view.findViewById(R.id.tv_gallery);
		TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

		tv_camera.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cameraClick();
				mAlertDialog.dismiss();
			}
		});
		tv_gallery.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				galleryClick();
				mAlertDialog.dismiss();
			}
		});

		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mAlertDialog.dismiss();
			}
		});
	}

	//打开相机
	public void cameraClick(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, CAMERA_REQUEST_CODE);
	}

	//打开图库
	public void galleryClick(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, GALLERY_REQUEST_CODE);
	}

	//保存图片，并返回图片相应的uri
	private Uri saveBitmap(Bitmap bm,boolean save)
	{
		//图片路径
		File tmpDir = new File(Environment.getExternalStorageDirectory() + "/health");
		//判断次路径是否存在，不存在则新建
		if(!tmpDir.exists())
		{
			tmpDir.mkdir();
		}
		File img = new File(tmpDir.getAbsolutePath() + "logo.png");
		try {
			FileOutputStream fos = new FileOutputStream(img);
			bm.compress(Bitmap.CompressFormat.PNG, 85, fos);
			fos.flush();
			fos.close();
			if (save) {
				ProgressUploadFile uploadFile = new ProgressUploadFile(RegisterActivity.this, img);
				uploadFile.upload();
			}
			//返回图片的uri
			return Uri.fromFile(img);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}

	//截取URI
	private Uri convertUri(Uri uri)
	{
		InputStream is = null;
		try {
			//获取图片流
			is = getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return saveBitmap(bitmap,false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	//对对应的uri图片进行截图
	private void startImageZoom(Uri uri)
	{
		//调用系统截图工具，设置相应参数，设置截取比例和回调参数
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		//开启回调模式
		startActivityForResult(intent, CROP_REQUEST_CODE);
	}

	/**
	 * activity回调
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CAMERA_REQUEST_CODE)
		{
			if(data == null)
			{
				return;
			}
			else
			{
				Bundle extras = data.getExtras();
				if(extras != null)
				{
					Bitmap bm = extras.getParcelable("data");
					Uri uri = saveBitmap(bm,false);
					startImageZoom(uri);
				}
			}
		}
		else if(requestCode == GALLERY_REQUEST_CODE)
		{
			if(data == null)
			{
				return;
			}
			Uri uri;
			uri = data.getData();
			Uri fileUri = convertUri(uri);
			startImageZoom(fileUri);
		}
		else if(requestCode == CROP_REQUEST_CODE)
		{
			if(data == null)
			{
				return;
			}
			Bundle extras = data.getExtras();
			if(extras == null){
				return;
			}
			Bitmap bm = extras.getParcelable("data");
			saveBitmap(bm, true);
			//            ImageView imageView = (ImageView)findViewById(R.id.imageView);
			//            imageView.setImageBitmap(bm);
			//            sendImage(bm);
			civ_register_image.setImageBitmap(bm);
		}
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case 0:

			break;

		default:
			break;
		}
	}
	/**
	 * 验证用户名	是否可用
	 * @param user_name
	 * @throws JSONException
	 */
	private void confirmName(String user_name) throws JSONException{
		final JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_name", user_name);
		OkHttpUtil.enqueue(OkHttpUtil.requestPostByJson(AppConfig.COMFIRM_USERNAME, jsonObject), new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {

			}

			@Override
			public void onResponse(Response response) throws IOException {
				Message message = new Message();
				message.what = 1;
				try {
					message.obj = response.body().string();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(message);
			}
		});
	}

	/**
	 * 注册
	 * @param user_name
	 * @param password
	 * @param phone
	 * @param age
	 * @param user_image
     * @throws Exception
     */
	private void register(String user_name,String password,String phone,String age,String user_image) throws Exception{
		final JSONObject object = new JSONObject();
		object.put("user_image", user_image);
		object.put("user_name", user_name);
		object.put("user_age", age);
		object.put("user_password", password);
		object.put("user_sex", sex);
		object.put("user_phone", phone);
		OkHttpUtil.enqueue(OkHttpUtil.requestPostByJson(AppConfig.REGISTER, object), new Callback() {
			@Override
			public void onFailure(Request request, IOException e) {

			}

			@Override
			public void onResponse(Response response) throws IOException {
				Message message = new Message();
				message.what = 2;
				try {
					message.obj = response.body().string();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(message);
			}
		});
	}


}