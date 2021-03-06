package com.reosrose.utildemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.reosrose.utildemo.R;
import com.reosrose.utildemo.base.UtilApplication;
import com.reosrose.utildemo.utils.DBUtils;
import com.reosrose.utildemo.utils.GoActivityUtil;
import com.reosrose.utildemo.utils.LogUtils;
import com.reosrose.utildemo.utils.ToastUtil;
import com.reosrose.utildemo.vo.UserVo;

public class LoginActivity extends BaseActivity implements TextWatcher{

    private EditText name;
    private EditText password;
    private Button login;
    private Button regist;
    private Context mContext;
    private String userName;
    private String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initView();
        initEvent();
    }

    private void initEvent() {
        name.addTextChangedListener(this);
        password.addTextChangedListener(this);
        login.setOnClickListener(this);
        regist.setOnClickListener(this);
    }

    private void initView() {
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        regist = (Button) findViewById(R.id.regist);
//        userName = "123";
//        userPassword = "123";
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        userName = name.getText().toString();
        userPassword = password.getText().toString();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.login:
                LogUtils.d("登录");
                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userPassword)){
                    ToastUtil.show(UtilApplication.getContext(),"用户名或密码不能为空");
                    return;
                }
                boolean flag = DBUtils.getIsExitByName(UtilApplication.getContext(),userName);
                if(flag){
                    UserVo userVo = DBUtils.getUserByName(UtilApplication.getContext(),userName);
                    if(userVo == null){
                        ToastUtil.show(UtilApplication.getContext(),"用户名不存在");
                    }
                    if(userPassword != null){
                        if(userPassword.equals(userVo.getPassword())){
                            GoActivityUtil.goToMainActivity(mContext);
                            finish();
                        }else {
                            ToastUtil.show(UtilApplication.getContext(),"密码错误");
                        }
                    }
                }else {
                    ToastUtil.show(UtilApplication.getContext(),"用户名不存在");
                }

                break;
            case R.id.regist:
                //注册
                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userPassword)){
                    ToastUtil.show(UtilApplication.getContext(),"用户名或密码不能为空");

                    return;
                }
                boolean isExitByName = DBUtils.getIsExitByName(UtilApplication.getContext(),userName);
                if(isExitByName){
                    ToastUtil.show(UtilApplication.getContext(),"用户名已存在");
                    return;
                }
                UserVo userVo = new UserVo();
                userVo.setUserName(userName);
                userVo.setPassword(userPassword);
               long result =  DBUtils.registUser(UtilApplication.getContext(),userVo);
                if(result == -1){
                    ToastUtil.show(UtilApplication.getContext(),"注册失败");
                }else {
                    ToastUtil.show(UtilApplication.getContext(),"注册成功");
                    GoActivityUtil.goToMainActivity(mContext);
                    finish();

                }
//
                break;
        }
    }
}
