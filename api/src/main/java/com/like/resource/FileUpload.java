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

    private String imageUserFaceLocation;


}
