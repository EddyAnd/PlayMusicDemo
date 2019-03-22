package com.example.playmusicdemo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.playmusicdemo.R;

public class InputView extends FrameLayout {
    private int inputIcon;
    private String inputHint;
    private boolean inputPassword;
    private View mView;
    private ImageView mImageView;
    private EditText mEditText;

    public InputView(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InputView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context,attrs);
    }

    /**
     * 初始化
     * @param context
     * @param attributeSet
     */
    private void init (Context context,AttributeSet attributeSet){
        if (attributeSet == null){
            return;
        }
        //获取在style中定义的各种属性值，然后根据获取到的不同的属性值实现差异化的效果。
        TypedArray  typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.InputView);
        inputIcon = typedArray.getResourceId(R.styleable.InputView_input_icon,R.mipmap.login_user);
        inputHint = typedArray.getString(R.styleable.InputView_input_hint);
        inputPassword = typedArray.getBoolean(R.styleable.InputView_input_cipher,false);
        //绑定layout布局
        mView = LayoutInflater.from(context).inflate(R.layout.input_view_layout,this,false);
        mImageView = mView.findViewById(R.id.iv_icon);
        mEditText = mView.findViewById(R.id.iv_edit_text);
       //布局关联属性
        mImageView.setImageResource(inputIcon);
        mEditText.setHint(inputHint);
        mEditText.setInputType(inputPassword ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_PHONE);
        this.addView(mView);

        typedArray.recycle();
    }


    /**
     * 获取输入内容
     * @return
     */
    public String getInputStr(){
        return mEditText.getText().toString().trim();
    }
}
