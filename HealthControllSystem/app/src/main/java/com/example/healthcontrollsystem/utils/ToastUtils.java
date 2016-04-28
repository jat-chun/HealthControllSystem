package com.example.healthcontrollsystem.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcontrollsystem.R;


/**
 * Created by Administrator on 2015/11/20.
 */
public class ToastUtils {
	
	/**
	 * 没有图标的toast
	 * @param msg
	 * @param context
	 */
    public static void showToast(String msg,Context context){
        Toast toast=new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view= LayoutInflater.from(context).inflate(R.layout.toast_style,null);
        TextView tv=(TextView)view.findViewById(R.id.text_toast_msg);
        ImageView im_toast = (ImageView) view.findViewById(R.id.im_toast);
        im_toast.setVisibility(View.GONE);
        tv.setText(msg);
        toast.setView(view);
        toast.show();
    }

    /**
     * 操作成功提示
     */
    public static void toastSucc(String msg,Context context){
        Toast toast=new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view= LayoutInflater.from(context).inflate(R.layout.toast_style,null);
        TextView tv=(TextView)view.findViewById(R.id.text_toast_msg);
        ImageView im=(ImageView)view.findViewById(R.id.im_toast);
        im.setImageResource(R.mipmap.toast_ok);
        tv.setText(msg);
        toast.setView(view);
        toast.show();
    }

    /**
     * 操作失败提示
     * @param msg
     * @param context
     */
    public static void toastError(String msg,Context context){
        Toast toast=new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        View view= LayoutInflater.from(context).inflate(R.layout.toast_style,null);
        TextView tv=(TextView)view.findViewById(R.id.text_toast_msg);
        ImageView im=(ImageView)view.findViewById(R.id.im_toast);
        im.setImageResource(R.mipmap.toast_error);
        tv.setText(msg);
        toast.setView(view);
        toast.show();
    }

}
