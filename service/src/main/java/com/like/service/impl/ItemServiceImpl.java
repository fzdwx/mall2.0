package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.enums.CommentLevel;
import com.like.mapper.*;
import com.like.pojo.*;
import com.like.pojo.vo.CommentLevelCountsVO;
import com.like.pojo.vo.ItemCommentVO;
import com.like.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

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

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        List<ItemsComments> rawList = itemsCommentsMapper.selectList(new QueryWrapper<ItemsComments>()
                .eq("item_id", itemId));

        Long goodCounts = getCommentCountsOfLevel(rawList, CommentLevel.GOOD.code);
        Long normalCounts = getCommentCountsOfLevel(rawList, CommentLevel.NORMAL.code);
        Long badCounts = getCommentCountsOfLevel(rawList, CommentLevel.BAD.code);
        Long totalCounts = goodCounts + normalCounts + badCounts;

        return new CommentLevelCountsVO(totalCounts, goodCounts, normalCounts, badCounts);
    }

    @Override
    public List<ItemCommentVO> queryPagedComments(String itemId, Integer level) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("itemId", itemId);
        param.put("level", level);

        return itemsMapper.queryItemComments(param);
    }

    /**
     * 根据传入的原始评论list找出和level对应的评论的个数
     *
     * @param rawList 原始列表
     * @param level   水平
     * @return {@link Long}
     */
    private Long getCommentCountsOfLevel(List<ItemsComments> rawList, Integer level) {
        return rawList.stream().filter(c -> Objects.equals(c.getCommentLevel(), level)).count();
    }

}
