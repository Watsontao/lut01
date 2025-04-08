package com.vincent.user.controller;

import com.vincent.user.pojo.User;
import com.vincent.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

/**
 * @@author vincent
 * @create2023-12-22-8:31
 * 用户的登录页
 * 用户注册页
 */
@Controller  //返回的字符串是一个页面的名称
@RequestMapping("/user")
public class UserPageController {

    @Autowired
    private UserService userServiceImpl;

    //这里只是返回页面，没有逻辑，不需要service参与,
    @RequestMapping("/login")
    public String login(){
        return "login";  //页面的名称
    }

    //
    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/add")
    public String add(User user){

        int result = userServiceImpl.add(user);


        if (result > 0 ){
            //添加成功
            return "forward:/user/login";
        }else {
            //添加失败
            return "forward:/user/register";
        }

    }

    //设置登陆成功页
    @RequestMapping("/login_success")
    public String loginSuccess(User user, int platform,  HttpServletRequest req){
        //判断用户名和密码是否正确
        User userTemp = userServiceImpl.findUserByNameAndPassword(user);
        if (userTemp != null) {
            //用户输入正确，写入session ,可以在商品列表页调用username，跳转到商品页
            req.getSession().setAttribute("username", userTemp.getUserName());
            req.getSession().setAttribute("userId",userTemp.getUserId());
            //用户不为空,跳转商品页
            if(platform==0){
                return "redirect:/commodity/list";
            }
            else{
                return "redirect:/admin/manage?className=com.vincent.commodity.pojo.Commodity";
            }

        } else {
            return "redirect:/user/login";
        }


        /*if (userTemp !=user ){
            //用户输入正确，写入session ,可以在商品列表页调用username，跳转到商品页
            req.getSession().setAttribute("username", userTemp.getUserName());
            req.getSession().setAttribute("userId",userTemp.getUserId());

            return "redirect:/commodity/list";
        }else {
            return "forward:/user/login";
        }*/
    }


    //个人中心修改用户，显示修改个人信息
    @RequestMapping("/update")
    public String updateUser(HttpServletRequest req, Model model){
        //先获取原来数据，作为模型传入前端，显示出内容，对内容进行修改
        //通过session取用户，用户名不重复，可以获取用户
        String username = (String) req.getSession().getAttribute("username");
        //根据用户id获取用户
        int userId = (int)req.getSession().getAttribute("userId");

        User user = userServiceImpl.findUserByUserId(userId);
        //把user存到model里，返回到前端
        model.addAttribute("user",user);
        return "updateUser";
    }

    //调用修改接口,点击更新用户信息
    @RequestMapping("/updateUser")
    public String updateByUserId(User user,HttpServletRequest req){
        //返回值是matched记录数（匹配到的行数）
        int resultOfUpdate = userServiceImpl.updateByUserId(user);
        req.getSession().setAttribute("username",user.getUserName());
        return "forward:/commodity/list";
    }
}
