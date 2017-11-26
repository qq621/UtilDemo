package com.reosrose.utildemo.vo;

import java.io.Serializable;

/**
 * Created by yinsxi on 2017/11/8.
 */

public class UserVo implements Serializable {

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

}
