package com.like.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.Items;
import com.like.pojo.ItemsImg;
import com.like.pojo.ItemsParam;
import com.like.pojo.ItemsSpec;
import com.like.pojo.vo.CommentLevelCountsVO;
import com.like.pojo.vo.ItemCommentVO;
import com.like.pojo.vo.SearchItemsVO;
import com.like.utils.PagedGridResult;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-11 10:22
 */
public interface ItemService extends IService<Items> {

    /**
     * 根据商品id查询详情
     *
     * @param itemId 商品id
     * @return {@link Items}
     */
    public Items queryItemById(String itemId);


    /**
     * 根据商品id查询商品图片列表
     *
     * @param itemId 商品id
     * @return {@link List<ItemsImg>}
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格列表
     *
     * @param itemId 商品id
     * @return {@link List<ItemsSpec>}
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品属性
     *
     * @param itemId 商品id
     * @return {@link List<ItemsParam>}
     */
    public ItemsParam queryItemParam(String itemId);


    /**
     * 查询商品对应的评论信息
     *
     * @param itemId 商品id
     * @return {@link CommentLevelCountsVO}
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 查询商品的评价 - 分页
     *
     * @param itemId 商品id
     * @param level  评价等级 good normal bad
     * @return {@link List<ItemCommentVO>}
     */
    public IPage<ItemCommentVO> queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品信息
     * k 默认 根据name
     * c 根据销量
     * p 根据价格
     *
     * @param keywords 关键字
     * @param sort     排序
     * @param page     页面
     * @param pageSize 页面大小
     * @return {@link PagedGridResult}
     */
    public IPage<SearchItemsVO> searchItems(String keywords, String sort, Integer page, Integer pageSize);

    /**
     * 根据三级分类id搜索商品信息
     *
     * @param catId    分类id
     * @param sort     排序
     * @param page     页面
     * @param pageSize 页面大小
     * @return {@link PagedGridResult}
     */
    public IPage<SearchItemsVO> searchItemsByThirdCategory(Integer catId, String sort, Integer page, Integer pageSize);
}
