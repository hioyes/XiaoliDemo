package com.xiaolidemo.ui.common;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.xiaoli.library.View.LoadingDialog;
import com.xiaoli.library.ui.BaseActivity;
import com.xiaoli.library.View.ClearEditText;
import com.xiaoli.library.utils.ForwardUtils;
import com.xiaoli.library.utils.StringUtils;
import com.xiaolidemo.R;
import com.xiaolidemo.ui.DemoActivity;

/**
 * 登陆页面
 * @author xiaokx on 2016-6-2 14:25
 * @Email:hioyes@qq.com
 */
public class LoginActivity extends BaseActivity {

    private RelativeLayout mRlLogin;

    //用户名
    private ClearEditText mEtUserName;
    //密码
    private EditText mEtPassword;

    //显示隐藏密码,清空密码
    private ImageButton mIbHidePwd,mIbClearPwd;

   //是否显示密码 默认不显示
    private boolean isPwdShow;

    //登陆按钮
    private Button mBtnLogin;


    @Override
    protected int getLayoutResId() {
        return R.layout.login;
    }

    @Override
    protected void initView() {
        mRlLogin = (RelativeLayout)findViewById(R.id.mRlLogin);
        mEtUserName = (ClearEditText)findViewById(R.id.mEtUserName);
        mEtPassword = (EditText)findViewById(R.id.mEtPassword);
        mIbHidePwd = (ImageButton)findViewById(R.id.mIbHidePwd);
        mIbClearPwd = (ImageButton)findViewById(R.id.mIbClearPwd);
        mBtnLogin = (Button)findViewById(R.id.mBtnLogin);
    }

    @Override
    protected void initListener() {
        mRlLogin.setOnClickListener(this);
        mIbHidePwd.setOnClickListener(this);
        mIbClearPwd.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        mEtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mIbClearPwd.setVisibility(View.VISIBLE);
                } else {
                    mIbClearPwd.setVisibility(View.INVISIBLE);
                }
            }
        });
        mEtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && !StringUtils.isNullOrEmpty(mEtPassword.getText().toString())) {
                    mIbClearPwd.setVisibility(View.VISIBLE);
                } else {
                    mIbClearPwd.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRlLogin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LoginActivity.this.hiddenKeyBoard();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.mIbHidePwd://显示隐藏密码
                isPwdShow = hidePwd(isPwdShow,mEtPassword,mIbHidePwd);
                break;
            case R.id.mIbClearPwd://清空密码
                mEtPassword.setText("");
                break;
            case R.id.mBtnLogin:
                hiddenKeyBoard();
                ForwardUtils.to(LoginActivity.this, DemoActivity.class);
                break;
        }

    }

    /**
     * 隐藏显示密码
     *
     * @param isPwdShow   当前密码是否显示  true-显示  false-隐藏
     * @param mEtPassword 输入密码的edittext
     * @param mIbHidePwd  imageButton点击它时执行此方法
     */
    public static boolean hidePwd(boolean isPwdShow, EditText mEtPassword, ImageButton mIbHidePwd) {
        if (isPwdShow) {
            mIbHidePwd.setImageResource(R.mipmap.login_pwd_hide);
            isPwdShow = false;
            int index = mEtPassword.getSelectionStart();
            mEtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mEtPassword.setSelection(index);
        } else {
            mIbHidePwd.setImageResource(R.mipmap.login_pwd_show);
            isPwdShow = true;
            int index = mEtPassword.getSelectionStart();
            mEtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mEtPassword.setSelection(index);
        }
        return isPwdShow;
    }

    /**
     * 隐藏软键盘
     */
    protected void hiddenKeyBoard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
