package com.vincent.common.pojo;

import com.vincent.category.pojo.Category;
import com.vincent.commodity.pojo.Commodity;
import com.vincent.user.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.thymeleaf.spring5.context.webmvc.SpringWebMvcThymeleafRequestContext;

import java.util.List;

/**
 * @@author vincent
 * @create2023-12-23-16:02
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Common {

  /*  commodity*/
    private int commodityId;

    private String commodityName;

    private String img;

//    private int categoryId;

    private int price;

    private int inventory;

//    private Category category; //commodity 和 category 多对一
//
//    private String categoryName;

    /*user*/
    private int userId;

    private String userName;

    private String password;

    private String phone;

    private String gender;

    private int balance;

    private String location;

    private int check;

    /*order*/
    private int orderId;

//    private int userId;

//    private int commodityId;

    private int totalPrice;

    String orderNo;

//    private User user;  //用户和订单是一对多

//    private List<Commodity> commodities;    //商品和订单是一对多


    /*category*/
    private int categoryId;

    private String categoryName;


    public static void main(String[] args) {
        Common com = new Common();
//        在Java类实例化时，基本类型的属性（如int）默认会赋予其对应的默认值，而不是null。int类型的默认值是0。
        System.out.println(com);
    }

}
