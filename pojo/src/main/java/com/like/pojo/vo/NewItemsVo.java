package com.like.pojo.vo;

import java.util.Date;
import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 最新商品vo 推荐位
 * @since 2021-02-10 19:29
 */
public class NewItemsVo {
    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;
    private Date createdTime;
    private List<SimpleItemVo> simpleItemList;

    public NewItemsVo(Integer rootCatId, String rootCatName, String slogan, String catImage, String bgColor, Date createTime, List<SimpleItemVo> simpleItemList) {
        this.rootCatId = rootCatId;
        this.rootCatName = rootCatName;
        this.slogan = slogan;
        this.catImage = catImage;
        this.bgColor = bgColor;
        this.createdTime = createTime;
        this.simpleItemList = simpleItemList;
    }

    public NewItemsVo() {
    }

    public Date getCreateTime() {
        return createdTime;
    }

    public void setCreateTime(Date createTime) {
        this.createdTime = createTime;
    }

    public Integer getRootCatId() {
        return rootCatId;
    }

    public void setRootCatId(Integer rootCatId) {
        this.rootCatId = rootCatId;
    }

    public String getRootCatName() {
        return rootCatName;
    }

    public void setRootCatName(String rootCatName) {
        this.rootCatName = rootCatName;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getCatImage() {
        return catImage;
    }

    public void setCatImage(String catImage) {
        this.catImage = catImage;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public List<SimpleItemVo> getSimpleItemList() {
        return simpleItemList;
    }

    public void setSimpleItemList(List<SimpleItemVo> simpleItemList) {
        this.simpleItemList = simpleItemList;
    }
}
