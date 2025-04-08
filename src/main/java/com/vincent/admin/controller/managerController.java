package com.vincent.admin.controller;


import com.vincent.common.pojo.CommOpper;
import com.vincent.common.pojo.Common;
import com.vincent.common.service.impl.commonServiceImpl;
import org.apache.catalina.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @@author vincent
 * @create2023-12-23-11:39
 */
@Controller
@RequestMapping("/admin")
public class managerController <T> {

    @Autowired
    private commonServiceImpl commonService;


    @RequestMapping("/manage")
    public String manageTable(String className, Model model, HttpServletRequest req){
        //首先显示什么样的数据，根据类来决定
        //反射技术可以进入这个类，然后获取全部属性

        //列表，每个表中的字段名称
        List<String>  list = new ArrayList<>();

        //不知道哪个表有几个field,直接返回前端是Common类,从表名后面开始走
        String tableName=  className.substring(className.lastIndexOf(".")+1);

        String tableNameLowCase = tableName.toLowerCase(); //变成小写

        String tableNameLowCaseTemp = tableNameLowCase; //temp save 一下，用于model.addAttribute("tableName",tableNameLowCaseTemp);

        if (tableNameLowCase.equals("order")){  //解决SQL语句中使用了MySQL的保留关键字"order"作为列名或表名导致的语法错误
            tableNameLowCase = "`"+tableNameLowCase+"`";
        }
        List<Common> tableColumnValues = commonService.selectAll(tableNameLowCase);

        try {
            //对象不能命名为class
            Class myClass = Class.forName(className);
            //可以直接获取里面的所有属性
            Field[] myFileds = myClass.getDeclaredFields();
            for(Field field:myFileds){
                list.add(field.getName());
//                System.out.println(field.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //表格项放在mytable_name中,
        model.addAttribute("tableColumnName",list);
        System.out.println("打印一下commonService.selectAll获取到的 tableColumnValues："+tableColumnValues);
        model.addAttribute("tableColumnValues",tableColumnValues);
//        model.addAttribute("tableName",className.toLowerCase());
        model.addAttribute("tableName",tableNameLowCaseTemp);
        model.addAttribute("className",className);   //转换成小写 然后取出来commodity等

        //存class_name,存在 session中，便于增删改查调用
        req.getSession().setAttribute("className",className);  //这里的className = com.vincent.User.pojo.User等...

        //取username
        String login_username=(String)req.getSession().getAttribute("username");
        //返回前台参数  导航栏
        model.addAttribute("username",login_username);
        //返回查询结果
        return "backManagement";
    }


    @RequestMapping("/del")
    public String manager_delete(CommOpper commopper, HttpServletRequest req){
        System.out.println("先看一下commopper："+commopper);
        //这里commonopper包含两个内容，一个内容是id，一个内容是table_name
        String className = (String) req.getSession().getAttribute("className");   //这里的className = com.vincent.User.pojo.User等...

        String tableName = className.substring ( className.lastIndexOf(".")+1 );
        String tableNameColumnId = tableName.toLowerCase()+"Id"; //设置一下表的id字段名

        System.out.println("commopper里的idColumnName："+tableNameColumnId);
        System.out.println("commopper里的tableName："+tableName.toLowerCase());

        //给commopper赋值，用于传输tableName，id，idColumnName数据到commoMapper.xml
        commopper.setTableName(tableName.toLowerCase());
        commopper.setIdColumnName(tableNameColumnId);

        System.out.println("删除前再检查一下commopper:"+commopper);
        commonService.delete(commopper);
        return "redirect:/admin/manage?className="+className;
    }


    //先实现返回添加数据的页面
    @RequestMapping("/add_page")
    public String add_page(Model model,HttpServletRequest req){

        //列表，每个表中的字段名称
        List<String>  list = new ArrayList<>();

        //从列表开始，className就存在
        String className=(String)req.getSession().getAttribute("className");
        System.out.println("++++++++++++++++++++++++++");
        System.out.println(className);
        System.out.println("+++++++++++++++++++++++++++++");

        try {
            //对象不能命名为class
            Class myClass = Class.forName(className);
            //可以直接获取里面的所有属性
            Field[] myFileds = myClass.getDeclaredFields();
            for(Field field:myFileds){
                //所有属性，不需要用户输入，
                if(field.getName().endsWith("id")){
                    //如果当前的字段的最后两个字符串是id，则继续下一次循环
                    continue;
                }
                list.add(field.getName());
                //System.out.println(field.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //添加数据把className放在session中
        req.getSession().setAttribute("className",className);
        //因为添加处理还需要一个table_name,这个做为model返回到前台中
        String tableName = className.substring(className.lastIndexOf(".")+1);
        System.out.println("/add_page 打印一下tableName："+tableName);
        model.addAttribute("tableName",tableName.toLowerCase());
        req.getSession().setAttribute("tableNameto/add_handler",tableName);
        //要把记录类的list返回前台
        model.addAttribute("mytable_name",list);
        //这个添加页面中有多少属性显示多少属性

        //取username   为了显示在导航栏
        String username=(String)req.getSession().getAttribute("username");
        model.addAttribute("username",username);

        return "back_manager_add";
    }

    //处理用户点击"添加"按钮产生,接收时对象只有Common类(设计表中所有属性)
    @RequestMapping("/add_handler")
    public String add_handler(Common common,HttpServletRequest req) {
        System.out.println("---------------");

        HttpSession session = req.getSession();
        //取出来tableName
        String tableName =(String) session.getAttribute("tableNameto/add_handler");
        session.removeAttribute("tableNameto/add_handler");

        System.out.println("打印一下现在的common,并判断一下common是否为空："+common);  //这里common里的categoryName = null  好奇怪
        System.out.println("打印一下从session获取的tableName："+tableName);


        System.out.println("---------------");
        //获取所有的参数的Common,只要为空就不接收了,这里建立HashMap,做动态的键值对的转换
        Map<String,Object> mymap=createMyMap(common);

        System.out.println("打印mymap："+ mymap);
        //把数据传给service
        CommOpper commopper=new CommOpper();
        commopper.setTableName(tableName.toLowerCase());
        commopper.setMaps(mymap);
        System.out.println("插入之前打印一下tableName："+tableName);
        commonService.add(commopper);
        //最后返回列表页
        String className=(String)req.getSession().getAttribute("className");
        System.out.println("/add_handler这里打印一下className，看提交之后跳转到哪个class的后台："+className);
        return "redirect:/admin/manage?className="+className;
    }




    //返回更新数据的面面
    @RequestMapping("/update_page")
    public String update_page(int id,Model model,HttpServletRequest req) {

        String className=(String)req.getSession().getAttribute("className");

        CommOpper commopper=new CommOpper();

        String tableName=className.substring(className.lastIndexOf(".")+1);
        System.out.println("/add_handler 打印一下 tableName:"+tableName);

        commopper.setTableName(tableName.toLowerCase());
        commopper.setId(id);
        HttpSession session = req.getSession();
        //把当前东西的id传给 update_handler
        session.setAttribute("idToUpdate_handler",id);

        System.out.println("-----------------------------------------------------");
        System.out.println("在执行getOneById之前检查一下commopper："+commopper);
        System.out.println("-----------------------------------------------------");

        Common common=commonService.getOneById(commopper);

        //结果可能是一个用户，也可能是一个商品，传到前端只显示有数据的字段，创立一个动态的HashMap
        //HashMap操作通过key_value来操作
        System.out.println("在执行getOneById之后打印一下common："+common);
        Map<String,Object> myEditOne = createMyMap(common);
        System.out.println("在执行createMyMap之后打印一下myEditOne："+myEditOne);

        //添加数据把class_name放在session中
        req.getSession().setAttribute("className",className);
        model.addAttribute("tableName",tableName.toLowerCase());
        model.addAttribute("myOneItem", myEditOne);

        //取username   为了显示在导航栏
        String username=(String)req.getSession().getAttribute("username");
        model.addAttribute("username",username);


        return "back_manager_update";

    }

    //更新页面的处理函数
    @RequestMapping("/update_handler")
    public String update_handler(Common common,String table_name,HttpServletRequest req) {

        System.out.println("/update_handler 打印一下common："+common);
        Map<String,Object> mymap = createMyMap(common);
        System.out.println("/update_handler 打印一下mymap："+mymap);

        //把数据传给service
        CommOpper commopper=new CommOpper();
        commopper.setTableName(table_name);
        commopper.setMaps(mymap);

        commopper.setId((int)req.getSession().getAttribute("idToUpdate_handler"));

        req.getSession().removeAttribute("idToUpdate_handler");

        System.out.println("打印一下commopper："+commopper);

        commonService.update(commopper);
        //最后返回列表页
        String className=(String)req.getSession().getAttribute("className");
        return "redirect:/admin/manage?className="+className;
    }












    //提取动态HashMap创建过程的语句，产生一个私有方法
    private Map<String, Object> createMyMap(Common common) {
        Map<String, Object> mymap = new HashMap<>();

        // 检查每个字段，将其添加到map中，这里不再进行类型转换
        //commodity
        if (common.getCommodityId() != 0) {
            mymap.put("commodityId", common.getCommodityId());
        }
        if (common.getCommodityName() != null && !common.getCommodityName().trim().isEmpty()) {
            mymap.put("commodityName", common.getCommodityName());
        }
        if (common.getPrice() != 0) {
            mymap.put("price", common.getPrice());
        }
        if (common.getInventory() != 0) {
            mymap.put("inventory", common.getInventory());
        }
        //category
        if (common.getCategoryId() != 0) {
            mymap.put("categoryId", common.getCategoryId());
        }

        //user

        if (common.getUserId() != 0) {
            mymap.put("userId", common.getUserId());
        }

        if (common.getUserName() != null && !common.getUserName().trim().isEmpty()) {
            mymap.put("userName", common.getUserName());
        }

        if (common.getPassword() != null && !common.getPassword().trim().isEmpty()) {
            mymap.put("password", common.getPassword());
        }

        if (common.getPhone() != null && !common.getPhone().trim().isEmpty()) {
            mymap.put("phone", common.getPhone());
        }
        if (common.getGender() != null && !common.getGender().trim().isEmpty()) {
            mymap.put("gender", common.getGender());
        }
        if (common.getBalance() != 0) {
            mymap.put("balance", common.getBalance());
        }
        if (common.getLocation() != null && !common.getLocation().trim().isEmpty()) {
            mymap.put("location", common.getLocation());
        }
        if (common.getCheck() != 0) {
            mymap.put("check", common.getCheck());
        }

        if (common.getTotalPrice() != 0) {
            mymap.put("totalPrice", common.getTotalPrice());
        }
        if (common.getOrderNo() != null) {
            mymap.put("orderNo", common.getOrderNo());
        }
        if (common.getCategoryName() != null && !common.getCategoryName().trim().isEmpty()) {
            mymap.put("categoryName", common.getCategoryName());
        }
        return mymap;
    }
//    private Map<String,String> createMyMap(Common common){
//
//        Map<String,String> mymap=new HashMap<>();
//
//        //构建所有值不为空的hashmap
//        mymap.put("id",null);
//        //根据传入的Commmon,返回一个没有空数据的HashMap
//        if(common.getCommodityName()!=null){
//            mymap.put("commodityName",common.getCommodityName());
//        };
//        if(common.getPrice()!=0){
//            //使用Integer.toString()方法转换成string类型
//            mymap.put("price",Integer.toString(common.getPrice()));
//        };
//
//        return mymap;
//    }



}










//        for(Common one:tableColumnValues){
////            System.out.println("---------------------打印出所得的common---------------------");
////            System.out.println(one);
////            System.out.println("commodity:");
////            System.out.println(one.getCommodityId());
////            System.out.println(one.getCommodityName());
////            System.out.println(one.getPrice());
////            System.out.println(one.getCategoryId());
////            System.out.println(one.getImg());
////            System.out.println(one.getInventory());
////            System.out.println("\n\n");
////
////            System.out.println("user:");
////            System.out.println(one.getUserId());
////            System.out.println(one.getUserName());
////            System.out.println(one.getGender());
////            System.out.println(one.getBalance());
////            System.out.println(one.getPhone());
////            System.out.println(one.getCheck());
////            System.out.println(one.getLocation());
////            System.out.println("\n\n");
////
////            System.out.println("pay:");
////            System.out.println(one.getPayId());
////            System.out.println("\n\n");
////
////            System.out.println("order:");
////            System.out.println(one.getOrderId());
////            System.out.println(one.getTotalPrice());
////            System.out.println(one.getOrderNo());
////            System.out.println("\n\n");
////
////            System.out.println("category:");
////            System.out.println(one.getCategoryId());
////            System.out.println(one.getCategoryName())
////            System.out.println("------------------------------------------\n");
//        }