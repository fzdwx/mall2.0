package com.like.pojo.bo;

import lombok.Data;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-17 18:51
 */
@Data
public class AddressBO {

    private String addressId;
    private String userId;
    private String receiver;
    private String mobile;
    private String province;
    private String city;
    private String district;
    private String detail;
}
