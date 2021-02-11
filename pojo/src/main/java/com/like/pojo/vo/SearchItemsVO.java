package com.like.pojo.vo;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-11 17:20
 */
public class SearchItemsVO {
    private Integer itemId;
    private String itemName;
    private Integer sellCounts;
    private String imgUrl;
    private Integer price; // 记录分 使用时除以100

    public SearchItemsVO() {
    }

    public SearchItemsVO(Integer itemId, String itemName, Integer sellCounts, String imgUrl, Integer price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.sellCounts = sellCounts;
        this.imgUrl = imgUrl;
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(Integer sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
