package com.like.pojo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-18 19:10
 * 用户订单创建的bo对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitOrderBO {
    private String userId;
    private String itemSpecIds;
    private String addressId;
    private Integer payMethod;
    private String leftMsg;
}
