package com.example.healthcontrollsystem.utils;

public class AppConfig {
	
	public static String BASE_URL = "http://192.168.1.114:8080/HealthServer/";

	public static String SHAREPREFERENCE = "userinfo_pref";
	public static String LOGIN = "login";
	public static String ISFIRST = "isFirst";
	public static String USER_NAME = "user_name";
	public static String USER_PASSWORD = "user_password";
	public static String USER_IMAGE = "user_image";
	public static String USER_AGE = "user_age";
	public static String USER_SEX = "user_sex";
	public static String USER_ID = "user_id";
	public static String USER_PHONE = "user_phone";
	public static String STEP_TOTAL = "step_total";
	public static String DATE = "date";
	public static String PLAN_STEP = "plan_step";
	public static String WEIGHT = "weight";
	public static String WEEK = "week";
	
	//上传头像
	public static String UPLOAD_IMAGE = BASE_URL+"ImageUploadServlet";
	//验证用户名
	public static String COMFIRM_USERNAME = BASE_URL+"ComfirmUserNameServlet";
	//用户注册
	public static String REGISTER = BASE_URL+"RegistersServlet";
	//登录
	public static String USER_LOGIN = BASE_URL+"LoginServlet";
	//分页获取记录
	public static String GETRECORDLIST = BASE_URL+"GetRecordList";
	//添加记录
	public static String ADD_RECORD = BASE_URL+"AddRecordServlet";
	//获取个人信息
	public static String PERSONAL_DETAILS = BASE_URL + "UserDetailsServlet";

	//mac_key
	public static String MAC_KEY = "mac_key";
	//call_id
	public static String CALL_ID = "call_id";
	//小米access_token
	public static String ACCESS_TOKEN = "access_token";
	//小米app_id
	public static long APP_ID = 2882303761517462810L;
	//小米key
	public static long APP_KEY = 5581746280810L;
	//小米secret
	public static String APP_SECRET = "5oof3oaFPkBLMaJLH83sKw==";
	//小米账号
	public static String EMAIL = "2511412745@qq.com";
	//小米密码
	public static String PASSWORD = "jat15088132300";
	//小米手环id
	public static String THIRD_APPID = "";
	//小米手环secret
	public static String THIRD_APPSECRET = "";
	//小米获取数据连接
	public static String XIAOMI_URL = "https://hmservice.mi-ae.com.cn/user/summary/getData";
}
