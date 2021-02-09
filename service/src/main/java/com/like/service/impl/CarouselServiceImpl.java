package com.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.like.mapper.CarouselMapper;
import com.like.pojo.Carousel;
import com.like.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-09 16:49
 */
@Service
public class CarouselServiceImpl implements CarouselService {
    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAllRootLevelCat(Integer isShow) {
        QueryWrapper<Carousel> query = new QueryWrapper<Carousel>()
                .eq("is_show", isShow);

        return carouselMapper.selectList(query);
    }
}
