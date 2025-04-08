package com.vincent.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @@author vincent
 * @create2023-12-23-12:01
 */

@Controller
@RequestMapping("/test_manager")
public class testManager {

    @RequestMapping("/other")
    public String test_other(){
       return "redirect:/admin/manage?class_name=com.user.pojo.User";
    }


}
