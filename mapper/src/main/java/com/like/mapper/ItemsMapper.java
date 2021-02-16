package com.like.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.like.my.mapper.MyMapper;
import com.like.pojo.Items;
import com.like.pojo.vo.ItemCommentVO;
import com.like.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsMapper extends MyMapper<Items> {

    IPage<ItemCommentVO> queryItemComments(Page<ItemCommentVO> p, @Param("param") Map<String, Object> param);

    IPage<SearchItemsVO> searchItems(Page<SearchItemsVO> p, @Param("param") Map<String, Object> param);

    List<SearchItemsVO> searchItemsByThirdCategory(@Param("param") Map<String, Object> param);
}