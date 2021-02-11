package com.like.pojo.vo;

import java.util.Date;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 商品评价vo
 * @since 2021-02-11 12:41
 */
public class ItemCommentVO {
    private Integer commentLevel;
    private String content;
    private String sepcName;
    private Date createTime;
    private String userFace;
    private String nickName;

    public ItemCommentVO() {
    }

    public ItemCommentVO(Integer commentLevel, String content, String sepcName, Date createTime, String userFace, String nickName) {
        this.commentLevel = commentLevel;
        this.content = content;
        this.sepcName = sepcName;
        this.createTime = createTime;
        this.userFace = userFace;
        this.nickName = nickName;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSepcName() {
        return sepcName;
    }

    public void setSepcName(String sepcName) {
        this.sepcName = sepcName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
