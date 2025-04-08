package com.vincent.order.controller;

import com.vincent.commodity.pojo.Commodity;
import com.vincent.order.pojo.Order;
import com.vincent.order.service.impl.orderServiceImpl;
import com.vincent.order.service.orderService;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;


/**
 * @@author vincent
 * @create2023-12-25-16:25
 */
@Controller
@RequestMapping("/makeOrder")
public class makeOrderController {

    @Autowired
    orderServiceImpl orderService = new orderServiceImpl();

    //展示订单
    @RequestMapping("/list")
    public String make_order_list(int orderNum, HttpServletRequest req, Model model) {
        //从session取出当前的commodity
        HttpSession session = req.getSession();
        Commodity commodity = (Commodity) session.getAttribute("OrderCommodity");
        //计算价钱 单价 * 数量       注意：这里tatalPrice老师定义的是double 我为了方便定义成int
        int totalPrice = commodity.getPrice() * orderNum;

        //定义订单
        Order order = new Order();
        order.setCommodityId(commodity.getCommodityId());
        order.setUserId((int) session.getAttribute("userId"));
        order.setTotalPrice(totalPrice);

        //把order存放到session里
        session.setAttribute("order",order);


        //取username   为了显示在前台导航栏
        String login_username = (String) req.getSession().getAttribute("username");
        //返回前台参数
        model.addAttribute("username", login_username);

        //把当前购买的数量也给放在session
        model.addAttribute("OrderCommodity", commodity);
        model.addAttribute("orderNum", orderNum);
        model.addAttribute("totalPrice", totalPrice);
        return "myOrder";
    }

    //确认订单
    @RequestMapping("/confirmOrder")
    public String confirm_order(HttpServletRequest req, Model model) {

        HttpSession session = req.getSession();
        Order order = (Order) session.getAttribute("order");

        //随机生成订单号
        order.setOrderNo(UUID.randomUUID().toString());

        System.out.println("打印一下order的数据："+order);

        //因为mybatis把这个插入返回的是插入的最后一条id
        int result = orderService.insert(order);
        if(result==1){
            System.out.println("插入order成功");
        }else {
            System.out.println("插入order失败");
        }

        //把最后一条信息的id放在orderId中，后面可以实现退单,增加的id结果在原对象中
        req.getSession().setAttribute("orderId", order.getOrderId());
        String username = (String) req.getSession().getAttribute("username");


        //取username
        String login_username = (String) req.getSession().getAttribute("username");
        //返回前台导航栏参数
        model.addAttribute("username", login_username);

        //如果往数据库里插入order成功，那么就成功，跳转到支付页面，否则跳转到商品列表页面
        if (result > 0) {
            return "redirect:/pay/success?total=" + order.getTotalPrice() + "&payMaster=" + username;
        } else {
            return "redirect:/commodity/list";
        }
    }

    //取消订单
    @RequestMapping("/cancel")
    public String cancelOrderAndPay(HttpServletRequest req, Model model) {
        //这里的id从session中取值
        int orderId = (int) req.getSession().getAttribute("orderId");
        //取username
        String login_username = (String) req.getSession().getAttribute("username");
        //返回前台参数
        model.addAttribute("username", login_username);
        System.out.println("-------------");
        System.out.println("打印一下要删除的order的orderId："+orderId);
        System.out.println("-------------");
        orderService.deleteByOrderId(orderId);
        return "forward:/pay/cancelPay";
    }


}
