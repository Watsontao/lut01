package com.vincent.systemAnalyse.controller;

import com.vincent.systemAnalyse.pojo.MyAnalyseOption;
import com.vincent.systemAnalyse.pojo.MyTempSystem;
import com.vincent.systemAnalyse.pojo.SystemResult;
import com.vincent.systemAnalyse.service.mySystemAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @@author vincent
 * @create2023-12-29-9:26
 */

@RestController
@RequestMapping("/analyse")
public class systemAnalyseController {
    @Autowired
    private mySystemAnalyseService systemAnalyseService;

    //读取文件的测试,读取文本文件FileReader
    @RequestMapping("/read_file_result")
    public String read_file_fetch_result(String info,String group_name,String group_fun){

        System.out.println("=======================");
        System.out.println(info);
        System.out.println(group_fun);
        System.out.println(group_name);
        System.out.println("=======================");
        //这里统计double，字符串这里没有完成通用
        String[] myinfos= {"cpu的用户用率","cpu的系统使用率",
                "cpu的空闲使用率","服务器总内存","服务器已用内存","服务器磁盘使用率"};
        List<MyTempSystem> mysystems = new ArrayList<>();
        List<SystemResult> myresultlists = new ArrayList<>();
        //实例化用户操作的意图类MyAnalyseOption
        MyAnalyseOption option=new MyAnalyseOption();
        //以小时来分
        option.setGroup_name(group_name);
        //求汇总,求最大
        option.setGroup_fun(group_fun);
        try {
            FileReader reader = new FileReader("F:/system.txt");
//            FileReader reader = new FileReader("F:/aa.log");
            BufferedReader buffer_reader = new BufferedReader(reader);

            int ch;
            while((ch=buffer_reader.read())!=-1){
                //buffer_read这里有一个readLine读取整行信息
                String line=buffer_reader.readLine();
                //先把|替换一下
                line=line.replace("|",",");
                //先把行按|分成数组
                String[] info_lines = line.split(",");
                //System.out.println("打印一下info_lines："+ Arrays.toString(info_lines));
                MyTempSystem mytemp=new MyTempSystem();
                mytemp.setMytime("\'2"+info_lines[0]+"\'");
//                mytemp.setMytime("2"+info_lines[0]);
                int index= Arrays.asList(myinfos).indexOf(info);
                //这里需要把一个String转换成Double.使用Double.parseDouble
                mytemp.setMyvalue(Double.parseDouble(info_lines[index+5]));
                System.out.println("打印一下mytemp："+mytemp);
                mysystems.add(mytemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("打印一下mysystems："+mysystems);
        System.out.println("--------------------------------------------------------------");
        myresultlists=systemAnalyseService.analyseData(mysystems,option);
        System.out.println("--------------------------------------------------------------");
        System.out.println("打印一下myresultlists："+myresultlists);
        System.out.println("--------------------------------------------------------------");
        return myresultlists.toString();
    }






}
