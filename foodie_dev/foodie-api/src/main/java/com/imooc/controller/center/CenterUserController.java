package com.imooc.controller.center;
import com.imooc.controller.BaseController;
import com.imooc.pojo.UserDO;
import com.imooc.pojo.bo.center.CenterUserBO ;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.imooc.utils.DateUtil.DATE_PATTERN;


@Api(value = "用户信息接口" ,tags = "用户信息相关接口")
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;
    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息" ,httpMethod = "POST")
    @PostMapping("uploadFace")
    public IMOOCJSONResult uploadFace(
                           @ApiParam(name="userId",value = "用户Id",required = true)
                           @RequestParam String userId,
                           @ApiParam(name="file",value = "用户头像",required = true)
                           MultipartFile file,
                           HttpServletRequest request,
                           HttpServletResponse response){

        //定义头像保存的位置
        String fileSpace=fileUpload.getImeagUserFaceLocation();
        //在路径上为每个用户增加一个userId,用于区分不同用户上传
        String uploadPrefix= File.separator+userId;
        //开始文件上传
        if(file!=null){
            FileOutputStream fileOutputStream=null;
            try {
                //获得文件上传的文件名称
                String fileName = file.getOriginalFilename();//文件原始的文件名
                if (fileName != null) {
                    //imooc-face.png ->  ["imooc-face","png"]
                    String fileNameArr[] = fileName.split("\\.");
                    //获取文件后缀名
                    String suffix = fileNameArr[fileNameArr.length - 1];
                    if(!suffix.equalsIgnoreCase("png")
                      &&!suffix.equalsIgnoreCase("jpg")
                      &&!suffix.equalsIgnoreCase("jpeg")){
                        return IMOOCJSONResult.errorMsg("图片格式不正确!");
                    }
                    //face-{userid}.png
                    //文件名称重组  覆盖是，增量式:额外拼接当前时间
                    String newFileName = "face-" + userId + "." + suffix;
                    //上传的头像最终保存的位置
                    String finalFacePath = fileSpace + uploadPrefix + File.separator + newFileName;
                    //用于提供给web服务的地址
                    uploadPrefix +=("/"+newFileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null) {
                        //创建文件
                        outFile.getParentFile().mkdirs();
                    }
                    //文件输出保存到目录
                     fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{
                if(fileOutputStream!=null){
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            return IMOOCJSONResult.errorMsg("文件不能为空");
        }
        //获取图片地址服务地址
        String imgeServerUrl=fileUpload.getImageServerUrl();
        //由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时刷新
        String finalUserFaceUrl= imgeServerUrl+uploadPrefix+"?t="+
                 DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);

        //用户头像更新到数据库
        UserDO userResult=centerUserService.updateUserFace(userId,finalUserFaceUrl);

        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);

        //TODO 后续会改，增加令牌token,会整合进redis,分布式会话
        return  IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "获取用户信息",notes = "获取用户信息" ,httpMethod = "POST")
    @PostMapping("update")
    public IMOOCJSONResult update(@ApiParam(name="userId",value = "用户Id",required = true)
                                  @RequestParam String userId,
                                  @RequestBody @Valid CenterUserBO centerUserBO,
                                  BindingResult result,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        //判断BindingResult 是否保存有错误，如果有，则直接return
        if(result.hasErrors()){
           Map<String,String> map=getErrors(result);
           return IMOOCJSONResult.errorMap(map);
        }
        UserDO userResult=centerUserService.updateUserInfo(userId,centerUserBO);


        //私有属性不保存到cookie中
        setNullProperty(userResult);
        //设置cookie true->加密
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);
        //TODO 后续会改，增加令牌token,会整合进redis,分布式会话
        return IMOOCJSONResult.ok("修改成功");
    }

    private UserDO setNullProperty(UserDO userResult){
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    private Map<String,String> getErrors(BindingResult result){
       Map<String,String> map=new HashMap<>();
       List<FieldError> errorList=result.getFieldErrors();
       for (FieldError erro:errorList){
           String errField=erro.getField();//发生验证错误对应的某个属性
           String errMsg  =erro.getDefaultMessage();//验证错误的信息
           map.put(errField,errMsg);
       }
       return  map;
    }
}
