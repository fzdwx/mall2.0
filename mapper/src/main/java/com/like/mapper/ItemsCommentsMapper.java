package com.like.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.like.my.mapper.MyMapper;
import com.like.pojo.ItemsComments;
import com.like.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemsCommentsMapper extends MyMapper<ItemsComments> {

    IPage<MyCommentVO> queryCommentList(Page<ItemsComments> pageInfo, @Param("userId") String userId);
}