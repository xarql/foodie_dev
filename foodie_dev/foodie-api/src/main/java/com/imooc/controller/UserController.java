package com.imooc.controller;
import com.imooc.pojo.UserDO;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

 /**
 * 判断用户名是否存在
 */
@RestController
//@ApiIgnore  隐藏该接口
@Api(value="用于用户登录的接口",tags = {"关于用户登录的接口"})
@RequestMapping("/passport")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在",notes ="用户名是否存在1",httpMethod="GET")
    @GetMapping("/usernameIsExist?username=")
    public IMOOCJSONResult equlseUserName(@RequestParam(required =true,name = "name") @NotBlank String name){
//     1.查找注册的用户名是否存在
       boolean isExist=userService.queryUsernameIsExist(name);
       if(isExist){
           return IMOOCJSONResult.ok();
       }
           return IMOOCJSONResult.errorMsg("没有该用户");
    }

    /**
     * 注册新建用户表
     */
    @ApiOperation(value = "用户注册",notes ="用户注册1",httpMethod="POST")
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBO userBO ,HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        String username=userBO.getUsername();
        String password=userBO.getPassword();
        String confirmPwd=userBO.getConfirmPassword();
        //0.判断用户名和密码必须不为空

        //1.查询用户名是否存在
        boolean isExist=userService.queryUsernameIsExist(userBO.getUsername());
        if(isExist){
            return IMOOCJSONResult.ok();
        }
        //2.密码长度不能少于6位
        if( password.length()<6){
           return IMOOCJSONResult.errorMsg("密码长度不能少于6");
        }
        //3.判断两次密码是否一致
        if(!password.equals(confirmPwd)){
            return IMOOCJSONResult.errorMsg("两次密码一致");
        }
        //4.实现注册
       UserDO userResult=userService.createuser(userBO);
        //私有属性不保存到cookie中
        setNullProperty(userResult);
        //设置cookie true->加密
        CookieUtils.setCookie(request,response,"user",
                JsonUtils.objectToJson(userResult),true);
        return IMOOCJSONResult.ok();
    }
     /**
      *登录功能
      */
     @ApiOperation(value = "用户登录",notes ="用户注册1",httpMethod="POST")
     @PostMapping("/login")
     public IMOOCJSONResult login(@RequestBody UserBO userBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
         String username=userBO.getUsername();
         String password=userBO.getPassword();
         //0.判断用户名和密码必须不为空
         if(userBO.getUsername()==null||userBO.getPassword()==null){
             return IMOOCJSONResult.errorMsg("用户名或密码不能为空");
         }
         //4.实现注册
         String id=MD5Utils.getMD5Str(password);
         UserDO userResult=userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
         if(userResult==null){
             return IMOOCJSONResult.errorMsg("用户名或密码不正确");
         }
         //私有属性不保存到cookie中
         setNullProperty(userResult);
         //设置cookie true->加密
         CookieUtils.setCookie(request,response,"user",
                 JsonUtils.objectToJson(userResult),true);
         //TODO 生成用户token,存入redis会话
         //TODO 同步购物车数据
         return IMOOCJSONResult.ok(userResult);
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

     /**
      *退出功能
      */
     @ApiOperation(value = "用户退出登录",notes ="用户注册1",httpMethod="POST")
     @PostMapping("/logout")
     public IMOOCJSONResult logout(@RequestParam String userId,
                                   HttpServletResponse response,
                                   HttpServletRequest request){
         //清除用户相关cookie;
         CookieUtils.deleteCookie(request,response,"user");
         //TODO 用户退出登录，需要清空购物车
         //TODO 分布式会话中需要清除用户数据
         return IMOOCJSONResult.ok();
     }
//     /**
//      *调用Session
//      */
//     @GetMapping("/setSession")
//     public Object getSession(HttpServletRequest request){
//         HttpSession session=  request.getSession();
//         session.setAttribute("userInfo","new user");
//         session.setMaxInactiveInterval(3600);        //设置时间
//         session.getAttribute("userInfo");
//         session.removeAttribute("userInfo");         // 删除
//         System.out.println(request.getRemoteAddr());
//         System.out.println("完整主机名");
//         System.out.println(request.getLocalAddr());
//         return "ok";
//     }
    }


