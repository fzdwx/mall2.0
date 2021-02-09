package com.like.service;

import com.like.pojo.Carousel;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 轮播图
 * @since 2021-02-09 16:47
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表的跟节点
     *
     * @param isShow 是否显示
     * @return {@link List<Carousel>}
     */
    public List<Carousel> queryAllRootLevelCat(Integer isShow);
}
