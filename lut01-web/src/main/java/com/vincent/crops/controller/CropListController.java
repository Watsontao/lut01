package com.vincent.crops.controller;

import com.vincent.crops.pojo.Crop;
import com.vincent.crops.service.impl.CropServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @@author vincent
 * @create2023-12-22-9:39
 */
@Controller
@RequestMapping("/api/crop")
public class CropListController {



    @Autowired
    private CropServiceImpl commodityService;

    //这里调用所有的接口，返回页面




    //返回显示所有商品的页面
    @RequestMapping("/list")
    public String commodityList(Model model, HttpServletRequest req){
        //查询所有
        List<Crop> commodities = commodityService.selectAll();

        String username = (String) req.getSession().getAttribute("username");

        //返回前台参数
        model.addAttribute("commodities",commodities);
        model.addAttribute("username",username);

        return "CropList";
    }

    //显示商品详情的页面
    @RequestMapping("/commodityDetail")
    public String findCommodityDetailById(Integer cropId , Model model, HttpServletRequest req){
        //根据id查到commodity
        System.out.println("打印一下当前commodity的commodityId："+cropId);
        Crop crop =  commodityService.selectByCropId(cropId);

        //把商品放在session中,便于order表中显示当前订单，在订单模块就可以调用
        req.getSession().setAttribute("OrderCommodity",crop);
        //取username   为了显示在导航栏
        String username=(String)req.getSession().getAttribute("username");
        int userId=(int)req.getSession().getAttribute("userId");
        //返回商品页时需要把查到的commodity传过去
        model.addAttribute("commodity",crop);
        model.addAttribute("userId",userId);
        model.addAttribute("username",username);
        return "CropDetail";
    }
}
