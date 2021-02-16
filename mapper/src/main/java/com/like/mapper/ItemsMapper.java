package com.like.mapper;

import com.like.my.mapper.MyMapper;
import com.like.pojo.vo.ItemCommentVO;
import com.like.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ItemsMapper extends MyMapper<Items> {

    List<ItemCommentVO> queryItemComments(@Param("param") Map<String, Object> param);

    List<SearchItemsVO> searchItems(@Param("param") Map<String, Object> param);

    List<SearchItemsVO> searchItemsByThirdCategory(@Param("param") Map<String, Object> param);
}