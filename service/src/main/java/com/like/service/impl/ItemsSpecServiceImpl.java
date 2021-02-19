package com.like.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.ItemsSpecMapper;
import com.like.pojo.ItemsSpec;
import com.like.service.ItemsSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:46
 */
@Service
public class ItemsSpecServiceImpl extends ServiceImpl<ItemsSpecMapper, ItemsSpec> implements ItemsSpecService {

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreaseItemSpecStock(String id, int buyCount) {
        // 防止超卖
        // synchronized加锁、锁表、分布式锁(zookeeper,redis)
        if (itemsSpecMapper.decreaseItemSpecStock(id, buyCount) != 1) {
            throw new RuntimeException("订单创建失败");
        }
    }
}
