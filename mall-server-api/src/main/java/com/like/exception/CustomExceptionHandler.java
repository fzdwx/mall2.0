package com.like.exception;

import com.like.utils.HttpJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author like
 * @email 980650920@qq.com
 * @description 自定义异常捕获
 * @since 2021-02-26 12:40
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    /**
     * 处理程序最大上传文件->不能超过512kb
     *
     * @param e MaxUploadSizeExceededException
     * @return {@link HttpJSONResult}
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public HttpJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {
        return HttpJSONResult.errorMsg("文件上传大小不能超过512kb,请压缩或降低图片质量");
    }
}
