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
import com.reosrose.utildemo.utils.DBUtils;
import com.reosrose.utildemo.utils.GoActivityUtil;
import com.reosrose.utildemo.utils.ToastUtil;
import com.reosrose.utildemo.vo.UserVo;

public class LoginActivity extends BaseActivity implements TextWatcher, View.OnClickListener{

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
                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userPassword)){
                    ToastUtil.show(mContext.getApplicationContext(),"用户名或密码不能为空");
                    return;
                }
                boolean flag = DBUtils.getIsExitByName(mContext.getApplicationContext(),userName);
                if(flag){
                    UserVo userVo = DBUtils.getUserByName(mContext.getApplicationContext(),userName);
                    if(password != null){
                        if(password.equals(userVo.getPassword())){
                            GoActivityUtil.goToMainActivity(mContext);
                            finish();
                        }else {
                            ToastUtil.show(mContext.getApplicationContext(),"密码错误");
                        }
                    }
                }else {
                    ToastUtil.show(mContext.getApplicationContext(),"用户名不存在");
                }

                break;
            case R.id.regist:
                //注册
                if(TextUtils.isEmpty(userName) && TextUtils.isEmpty(userPassword)){
                    ToastUtil.show(mContext.getApplicationContext(),"用户名或密码不能为空");

                    return;
                }
                boolean isExitByName = DBUtils.getIsExitByName(mContext.getApplicationContext(),userName);
                if(isExitByName){
                    ToastUtil.show(mContext.getApplicationContext(),"用户名已存在");
                    return;
                }
                UserVo userVo = new UserVo();
                userVo.setUserName(userName);
                userVo.setPassword(userPassword);
               long result =  DBUtils.registUser(mContext.getApplicationContext(),userVo);
                if(result == -1){
                    ToastUtil.show(mContext.getApplicationContext(),"注册失败");
                }else {
                    ToastUtil.show(mContext.getApplicationContext(),"注册成功");
                    GoActivityUtil.goToMainActivity(mContext);
                    finish();

                }
                break;
        }
    }
}
