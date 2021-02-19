package com.like.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.like.mapper.ItemsSpecMapper;
import com.like.pojo.ItemsSpec;
import com.like.service.ItemsSpecService;
import org.springframework.stereotype.Service;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-19 17:46
 */
@Service
public class ItemsSpecServiceImpl extends ServiceImpl<ItemsSpecMapper, ItemsSpec> implements ItemsSpecService {

    @Override
    public void decreaseItemSpecStock(String id, int buyCount) {

    }
}
