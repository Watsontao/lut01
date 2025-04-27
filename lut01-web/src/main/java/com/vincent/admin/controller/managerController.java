package com.vincent.admin.controller;

import com.vincent.common.pojo.CommOpper;
import com.vincent.common.pojo.Common;
import com.vincent.common.service.impl.commonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class managerController<T> {

    @Autowired
    private commonServiceImpl commonService;

    @RequestMapping("/manage")
    public String manageTable(String className, Model model, HttpServletRequest req){
        List<String> list = new ArrayList<>();
        String tableName = className.substring(className.lastIndexOf(".") + 1);
        String tableNameLowCase = tableName.toLowerCase();

        if ("user".equals(tableNameLowCase)) tableNameLowCase = "users";
        if ("crop".equals(tableNameLowCase)) tableNameLowCase = "crops";
        if ("task".equals(tableNameLowCase)) tableNameLowCase = "tasks";
        if ("greenhouse".equals(tableNameLowCase)) tableNameLowCase = "greenhouses";
        if ("order".equals(tableNameLowCase)) tableNameLowCase = "`order`";

        String tableNameLowCaseTemp = tableNameLowCase;
        List<Common> tableColumnValues = commonService.selectAll(tableNameLowCase);

        try {
            Class<?> myClass = Class.forName(className);
            Field[] myFields = myClass.getDeclaredFields();
            for (Field field : myFields) list.add(field.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("tableColumnName", list);
        model.addAttribute("tableColumnValues", tableColumnValues);
        model.addAttribute("tableName", tableNameLowCaseTemp);
        model.addAttribute("className", className);
        req.getSession().setAttribute("className", className);
        model.addAttribute("username", req.getSession().getAttribute("username"));

        return "backManagement";
    }

    @RequestMapping("/del")
    public String manager_delete(CommOpper commopper, HttpServletRequest req){
        String className = (String) req.getSession().getAttribute("className");
        String tableName = className.substring(className.lastIndexOf(".") + 1);
        String tableNameColumnId = tableName.toLowerCase() + "Id";

        commopper.setTableName(tableName.toLowerCase());
        commopper.setIdColumnName(tableNameColumnId);

        commonService.delete(commopper);
        return "redirect:/admin/manage?className=" + className;
    }

    @RequestMapping("/add_page")
    public String add_page(Model model, HttpServletRequest req){
        List<String> list = new ArrayList<>();
        String className = (String) req.getSession().getAttribute("className");

        try {
            Class<?> myClass = Class.forName(className);
            Field[] myFields = myClass.getDeclaredFields();
            for (Field field : myFields) {
                if (field.getName().endsWith("id")) continue;
                list.add(field.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("className", className);
        String tableName = className.substring(className.lastIndexOf(".") + 1);
        model.addAttribute("tableName", tableName.toLowerCase());
        req.getSession().setAttribute("tableNameto/add_handler", tableName);
        model.addAttribute("mytable_name", list);
        model.addAttribute("username", req.getSession().getAttribute("username"));

        return "back_manager_add";
    }

    @RequestMapping("/add_handler")
    public String add_handler(Common common, HttpServletRequest req) {
        HttpSession session = req.getSession();
        String tableName = (String) session.getAttribute("tableNameto/add_handler");
        session.removeAttribute("tableNameto/add_handler");

        Map<String, Object> mymap = createMyMap(common);

        CommOpper commopper = new CommOpper();
        commopper.setTableName(tableName.toLowerCase());
        commopper.setMaps(mymap);
        commonService.add(commopper);

        String className = (String) req.getSession().getAttribute("className");
        return "redirect:/admin/manage?className=" + className;
    }

    @RequestMapping("/update_page")
    public String update_page(int id, Model model, HttpServletRequest req) {
        String className = (String) req.getSession().getAttribute("className");
        String tableName = className.substring(className.lastIndexOf(".") + 1);

        CommOpper commopper = new CommOpper();
        commopper.setTableName(tableName.toLowerCase());
        commopper.setId(id);
        req.getSession().setAttribute("idToUpdate_handler", id);

        Common common = commonService.getOneById(commopper);
        Map<String, Object> myEditOne = createMyMap(common);

        req.getSession().setAttribute("className", className);
        model.addAttribute("tableName", tableName.toLowerCase());
        model.addAttribute("myOneItem", myEditOne);
        model.addAttribute("username", req.getSession().getAttribute("username"));

        return "back_manager_update";
    }

    @RequestMapping("/update_handler")
    public String update_handler(Common common, String table_name, HttpServletRequest req) {
        Map<String, Object> mymap = createMyMap(common);

        CommOpper commopper = new CommOpper();
        commopper.setTableName(table_name);
        commopper.setMaps(mymap);
        commopper.setId((int) req.getSession().getAttribute("idToUpdate_handler"));
        req.getSession().removeAttribute("idToUpdate_handler");

        commonService.update(commopper);
        String className = (String) req.getSession().getAttribute("className");
        return "redirect:/admin/manage?className=" + className;
    }

    // 通用 map 提取方法：反射自动判断非空、非零字段
    private Map<String, Object> createMyMap(Common common) {
        Map<String, Object> mymap = new HashMap<>();
        try {
            Class<?> clazz = common.getClass();   //获取 Common 类的所有属性字段。
            Field[] fields = clazz.getDeclaredFields();

            //遍历所有字段并取得每个字段的值。
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(common);

                //跳过无效数据：null、空字符串、数值型为0的字段，不参与 insert/update 操作。
                if (value == null) continue;
                if (value instanceof String && ((String) value).trim().isEmpty()) continue;
                if ((value instanceof Integer && (Integer) value == 0) ||
                        (value instanceof Double && (Double) value == 0.0) ||
                        (value instanceof Float && (Float) value == 0.0f)) continue;

                String key = Character.toLowerCase(field.getName().charAt(0)) + field.getName().substring(1);
                mymap.put(key, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return mymap;
    }
}
