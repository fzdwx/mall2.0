package com.like.resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-25 17:52
 */
@PropertySource("classpath:file-upload-dev.properties")
@ConfigurationProperties(prefix = "fileupload")
@Component
@Getter
@Setter
public class FileUpload {

    /**
     * 用户头像保存在本地的路径
     */
    private String imageUserFaceLocation;
    /**
     * 用户头像在web页面访问的前缀 + 210220AW6WGTZ8BC/face-210220AW6WGTZ8BC.jpg
     */
    private String imageServerUlrPrefix;


}
