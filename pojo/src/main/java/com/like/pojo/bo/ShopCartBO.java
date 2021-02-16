package com.like.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-14 17:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCartBO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private Integer buyCounts;
    private String priceDiscount;
    private String priceNormal;
}
