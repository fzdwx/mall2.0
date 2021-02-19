package com.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.ItemsSpec;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:46
 */
public interface ItemsSpecService extends IService<ItemsSpec> {

    /**
     * 扣除库存
     *
     * @param id       id
     * @param buyCount 购买数
     */
    void decreaseItemSpecStock(String id, int buyCount);
}
