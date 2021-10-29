package com.imooc.exception;
import com.imooc.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 捕获异常类
 */
@RestControllerAdvice
public class CustomExceptionHandler {
    //上传文件超过500k捕获异常（200k-500k）
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException e){
        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500kb,请压缩图片或者降低图片质量再上传");
    }
}
