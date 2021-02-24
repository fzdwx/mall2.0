package com.like.pojo.bo.center;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-24 16:38
 */
@Data
public class UserCenterBo {
    private String username;
    private String password;
    private String confirmPassword;
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "Z用户昵称不能超过12位")
    private String nickname;
    @NotBlank(message = "用户昵称不能为空")
    @Length(max = 12, message = "Z用户昵称不能超过12位")
    private String realname;
    @Email
    private String email;
    @Pattern(regexp = "^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\\d{8})$", message = "手机号格式不正确")
    private String mobile;
    @Min(value = 0, message = "性别选择不正确")
    @Max(value = 2, message = "性别选择不正确")
    private String sex;
    private String birthday;
}
