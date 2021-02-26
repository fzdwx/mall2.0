package com.like.controller.center;

import com.like.controller.BaseController;
import com.like.pojo.Users;
import com.like.pojo.bo.center.UserCenterBo;
import com.like.resource.FileUpload;
import com.like.service.center.UserCenterService;
import com.like.utils.CookieUtils;
import com.like.utils.DateUtil;
import com.like.utils.HttpJSONResult;
import com.like.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 用户中心
 * @since 2021-02-24 15:27
 */
@Api(value = "用户中心", tags = "用户中心相关接口")
@RestController
@RequestMapping("userInfo")
@Slf4j
public class UserCenterController extends BaseController {

    @Autowired
    UserCenterService userCenterService;
    @Autowired
    FileUpload fileUpload;

    @PostMapping("userface")
    @ApiOperation(value = "修改用户头像", notes = "修改用户头像")
    public HttpJSONResult userface(
            @RequestParam String userId,
            MultipartFile file,
            HttpServletRequest req, HttpServletResponse reps) {

        if (file != null) {
            FileOutputStream fos = null;
            InputStream is = null;
            try {
                // 定义头像的保存地址
//                String filePath = IMAGE_USER_FACE_LOCATION;
                String filePath = fileUpload.getImageUserFaceLocation();
                String uploadPrefix = File.separator + userId;
                String suffix;
                String rawName = file.getOriginalFilename();
                String finallyFacePath;
                if (StringUtils.isNotBlank(rawName)) {   // face-{userId}.png
                    String[] s = rawName.split("\\.");
                    suffix = s[s.length - 1];  // 获取后缀名
                    // 检测后缀
                    if (!suffix.equalsIgnoreCase("png") &&
                            !suffix.equalsIgnoreCase("jpg") &&
                            !suffix.equalsIgnoreCase("jpeg")) {
                        return HttpJSONResult.errorMsg("图片格式不正确，必须为png，jpg，jpeg");
                    }

                    String newName = "face-" + userId + "." + suffix;

                    finallyFacePath = filePath + uploadPrefix + File.separator + newName;

                    File outFile = new File(finallyFacePath);
                    if (outFile.getParentFile() != null) {
                        // 创建文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fos = new FileOutputStream(outFile);
                    is = file.getInputStream();
                    IOUtils.copy(is, fos);

                    // 定义访问路径  由于浏览器可能存在缓存 所以加上 时间戳
                    String path = fileUpload.getImageServerUlrPrefix() + "/" + userId + "/" + newName
                            + "?t=" + new Date().getTime();
                    log.info("用户新头像访问地址：{}", path);
                    // 更新用户头像
                    Users dbUser = userCenterService.updateUserFace(userId, path);

                    // 保护用户隐私信息
                    setNullProperty(dbUser);
                    CookieUtils.setCookie(req, reps,
                            "user",
                            JsonUtils.objectToJson(dbUser), true);
                }
                return HttpJSONResult.ok();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return HttpJSONResult.errorMsg("文件不能为空");
    }

    @PostMapping("update/{userId}")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息")
    public HttpJSONResult update(
            @PathVariable String userId,
            @RequestBody UserCenterBo user,
            BindingResult bindResult,
            HttpServletRequest req, HttpServletResponse reps) {
        // 验证用户信息是否正确
        if (bindResult.hasErrors()) {
            return HttpJSONResult.errorMap(getErrors(bindResult));
        }

        Users resUser = userCenterService.updateUseUserCenterBO(userId, user);
        // 保护用户隐私信息
        setNullProperty(resUser);
        CookieUtils.setCookie(req, reps,
                "user",
                JsonUtils.objectToJson(resUser), true);
        // TODO: 2021/2/24 后续会更改，增加token，整合到redis，分布式会话
        return HttpJSONResult.ok();
    }

    /**
     * 得到后端校验错误
     *
     * @param bindingResult 绑定的结果
     * @return {@link Map<String, String>}
     */
    private Map<String, String> getErrors(BindingResult bindingResult) {
        Map<String, String> fieldMapErrorInfo = new HashMap<String, String>();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            String field = error.getField();
            String message = error.getDefaultMessage();
            fieldMapErrorInfo.put(field, message);
        }

        return fieldMapErrorInfo;
    }

    private void setNullProperty(Users u) {
        u.setPassword("");
        u.setMobile("");
        u.setEmail("");
        u.setBirthday(DateUtil.stringToDate(""));
        u.setCreatedTime(DateUtil.stringToDate(""));
        u.setUpdatedTime(DateUtil.stringToDate(""));
    }
}
