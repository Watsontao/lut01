package com.vincent.pay.controller;

import com.vincent.pay.pojo.Pay;
import com.vincent.pay.service.impl.payServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/pay")
public class paySuccessController {

    @Autowired
    private payServiceImpl payService;
    @RequestMapping("/success")
    public String insert(Pay pay, HttpServletRequest req, Model model) {

        System.out.println("打印一下从前端传送来的pay："+pay);
        //这里打印pay之后发现传来的是Pay(payId=null, totalPrice=null, UserId=null, payMaster=??)，因此设置一下pay的信息
        //取username
        String login_username=(String)req.getSession().getAttribute("username");
        //返回前台参数
        model.addAttribute("username",login_username);
        //当前支付也返回最后一条id，result返回最后一条数据
        int result=payService.insert(pay);
        if(result==1){
            System.out.println("插入pay成功");
        }else {
            System.out.println("插入pay失败");
        }

        //最终添加的最后一条id在record里,不在result结果中
        req.getSession().setAttribute("payId",pay.getPayId());
        if(result>0){
            return "myPay";
        }else{
            return "/commodity/list";
        }
    }


    @RequestMapping("/cancelPay")
    public String cancel_pay(HttpServletRequest req,Model model){
        //取username
        String login_username=(String)req.getSession().getAttribute("username");
        //返回前台参数
        model.addAttribute("username",login_username);
        int payId=(int)req.getSession().getAttribute("payId");
        System.out.println("-------------");
        System.out.println("打印一下要删除的pay的payId："+payId);
        System.out.println("-------------");
        payService.deleteByPayId(payId);
        return "cancel";
    }

}
