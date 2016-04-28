package com.example.healthcontrollsystem.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.healthcontrollsystem.R;


/**
 * 自定义dialog
 * Created by Administrator on 2015/11/20.
 */
public class CustomDialog implements View.OnClickListener{
    private Dialog mDialog;
    private View mView;
    private TextView mTvMsg,mTvConfim,mTvCancel;

    private OnSingleClickListener mSingleListener;
    private OnMutiClickListener mMutiListener;

    private boolean isMuti;

    public CustomDialog(Context context,boolean isMuti){
        init(context);
        this.isMuti=isMuti;
        setMode();
    }

    private void setMode(){
        if (isMuti){
            mTvCancel.setVisibility(View.VISIBLE);
            mView.findViewById(R.id.view_division_ver).setVisibility(View.VISIBLE);
        }else {
            mTvCancel.setVisibility(View.GONE);
            mView.findViewById(R.id.view_division_ver).setVisibility(View.GONE);
        }
    }

    private void init(Context context){
        mDialog=new Dialog(context, R.style.CustomDialogTheme);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);




//        mDialog.getWindow().setLayout(DisplayUtils.pxToDp(230,context), WindowManager.LayoutParams.WRAP_CONTENT);
//        WindowManager.LayoutParams wlp=mDialog.getWindow().getAttributes();
//        wlp.gravity=Gravity.CENTER;
//        wlp.width= DisplayUtils.pxToDp(230,context);
//        mDialog.getWindow().setAttributes(wlp);

        mView=LayoutInflater.from(context).inflate(R.layout.dialog_custom,null);
        mTvMsg=(TextView)mView.findViewById(R.id.text_dialog_msg);
        mTvConfim=(TextView)mView.findViewById(R.id.bt_confirm);
        mTvCancel=(TextView)mView.findViewById(R.id.bt_cancel);

        mTvConfim.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);

        mDialog.setContentView(mView);

        WindowManager.LayoutParams p = mDialog.getWindow().getAttributes(); // 获取对话框当前的参数值
        p.height = DisplayUtils.dpToPx(170,context); // 窗口高度
        p.width = DisplayUtils.dpToPx(230,context); // 窗口宽度
        mDialog.getWindow().setAttributes(p);
    }

    public void setCanCanceledOnTouchOutside(boolean flag){
    	mDialog.setCanceledOnTouchOutside(flag);
    }
    
    public void show(){
        if (!mDialog.isShowing()){
            mDialog.show();
        }
    }

    public void dismiss(){
        if(mDialog.isShowing()){
            mDialog.dismiss();
        }
    }

    public CustomDialog setShowingMsg(String msg){
        mTvMsg.setText(msg);
        return this;
    }
    
    public void setButtonText(String btn1,String btn2){
    	  mTvConfim.setText(btn1);
          mTvCancel.setText(btn2);
    }

    public void setOnMutiClickListener(OnMutiClickListener onMutiClickListener){
        mMutiListener=onMutiClickListener;
    }

    public void setOnSingleClickListener(OnSingleClickListener onSingleClickListener){
        mSingleListener=onSingleClickListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_confirm:
                if (!isMuti){
                    if (mSingleListener!=null) mSingleListener.onClick();
                }else{
                    if (mMutiListener!=null) mMutiListener.onClickLeft();
                }
                dismiss();
                break;
            case R.id.bt_cancel:
                if (isMuti&&mMutiListener!=null) mMutiListener.onClickRight();
                dismiss();
                break;
        }
    }

    public interface OnMutiClickListener{
        void onClickLeft();
        void onClickRight();
    }

    public interface OnSingleClickListener{
        void onClick();
    }
}
