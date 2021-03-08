package com.like.pojo;

import com.like.pojo.bo.ShopCartBO;
import com.like.pojo.vo.MerchantOrdersVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-21 15:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    private String orderId;
    private MerchantOrdersVO merchant;
    private List<ShopCartBO> removeShopCart;
}
