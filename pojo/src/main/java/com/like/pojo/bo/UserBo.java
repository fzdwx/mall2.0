package com.like.pojo.bo;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-07 12:51
 */
@Data
public class UserBo {
    private String username;
    private String password;
    private String confirmPassword;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
