package com.like.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.like.pojo.ItemsComments;
import com.like.pojo.bo.center.OrderItemsCommentBO;
import com.like.pojo.vo.MyCommentVO;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-27 13:47
 */
public interface ItemsCommentsService extends IService<ItemsComments> {

    void saveComments(String userId, String orderId, List<OrderItemsCommentBO> commentBOs);

    IPage<MyCommentVO> queryCommentList(Integer page, Integer pageSize, String userId);
}
