package com.vincent.commodity.pojo;


import com.vincent.category.pojo.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commodity {
    //主键 商品id
    private int commodityId;

    private String commodityName;

    private String img;

    //外键，种类id
    private int categoryId;

    private int price;

    private int inventory;

    //commodity 和 category 多对一
    private Category category;

    private String categoryName;

    public static void main(String[] args) {
        Commodity commodity =new Commodity();
        commodity.getImg();
    }


}