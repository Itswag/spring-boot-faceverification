package cn.srblog.faceverification.controller;

import cn.srblog.faceverification.common.Result;
import cn.srblog.faceverification.entity.User;
import cn.srblog.faceverification.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(HttpSession httpSession,String username,String password){
        User user = userService.login(username, password);
        if (user == null){
            return Result.createByErrorMessage("登录失败");
        }
        httpSession.setAttribute("onlineUser",user);
        return Result.createBySuccess(user);
    }

    @PostMapping("/register")
    public Result register(User user,HttpSession session){
        User register = userService.register(user);
        if (register == null){
            return Result.createByErrorMessage("注册失败");
        }
        session.setAttribute("onlineUser",register);
        return Result.createBySuccess("注册成功",register);
    }

    @PostMapping("/face_login")
    public Result login1(HttpSession httpSession,String BASE64){
        User user = userService.faceLogin(BASE64);
        if (user == null){
            return Result.createByErrorMessage("登录失败");
        }
        httpSession.setAttribute("onlineUser",user);
        return Result.createBySuccess(user);
    }

    @GetMapping("/logout")
    public Result logout(HttpSession httpSession){
         httpSession.removeAttribute("onlineUser");
         return Result.createBySuccess();
    }

    @PostMapping("/addFace")
    public Result addFace(String BASE64,HttpSession httpSession){
        User user = (User) httpSession.getAttribute("onlineUser");
        boolean result = userService.addFace(user.getId(), BASE64);
        if (result){
            return Result.createBySuccess("上传成功");
        }else{
            return Result.createByErrorMessage("上传失败");
        }
    }

    @PostMapping("/face_status")
    public Result faceNumber(HttpSession httpSession){
        User user = (User) httpSession.getAttribute("onlineUser");
        int resultCount = userService.isRegFace(user.getId());
        if (resultCount == 0){
            return Result.createBySuccess("尚未注册",resultCount);
        }else if(resultCount == 20){
            return Result.createBySuccess("数量上限!",resultCount);
        }else{
            return Result.createBySuccess("已添加",resultCount);
        }
    }
}
