package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.mapper.ItemsImgMapper;
import com.like.mapper.ItemsMapper;
import com.like.mapper.ItemsParamMapper;
import com.like.mapper.ItemsSpecMapper;
import com.like.pojo.Items;
import com.like.pojo.ItemsImg;
import com.like.pojo.ItemsParam;
import com.like.pojo.ItemsSpec;
import com.like.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-11 10:24
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;
    @Autowired
    private ItemsImgMapper itemsImgMapper;
    @Autowired
    private ItemsSpecMapper itemsSpecMapper;
    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Items queryItemById(String itemId) {
        return itemsMapper.selectById(itemId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsImg> queryItemImgList(String itemId) {
        QueryWrapper<ItemsImg> query = new QueryWrapper<ItemsImg>().eq("item_id", itemId);
        return itemsImgMapper.selectList(query);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        QueryWrapper<ItemsSpec> query = new QueryWrapper<ItemsSpec>().eq("item_id", itemId);
        return itemsSpecMapper.selectList(query);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ItemsParam queryItemParam(String itemId) {
        QueryWrapper<ItemsParam> query = new QueryWrapper<ItemsParam>().eq("item_id", itemId);
        return itemsParamMapper.selectOne(query);
    }
}
