package com.vincent.systemAnalyse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @@author vincent
 * @create2023-12-29-10:56
 */
@Controller
@RequestMapping("/analysePage")
public class systemAnalysePageController {

    //需要一个前端页面，需要输入这些参数info,统计单位group_name,统计的汇总函数group_fun
    //返回一个用户统计意图的页面
    @RequestMapping("/show")
    public String show_analyse_page() {
        return "user_analyse";
    }
}
