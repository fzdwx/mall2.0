package com.like.pojo.vo;

import com.like.pojo.ItemsImg;
import com.like.pojo.ItemsParam;
import com.like.pojo.ItemsSpec;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 返回商品信息的Vo
 * @since 2021-02-11 10:48
 */
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> itemImgList;
    private List<ItemsSpec> itemSpecList;
    private ItemsParam itemParams;

    public ItemInfoVo(Items item, List<ItemsImg> imgs, List<ItemsSpec> specs, ItemsParam param) {
        this.item = item;
        this.itemImgList = imgs;
        this.itemSpecList = specs;
        this.itemParams = param;
    }

    public ItemInfoVo() {
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getItemImgList() {
        return itemImgList;
    }

    public void setItemImgList(List<ItemsImg> itemImgList) {
        this.itemImgList = itemImgList;
    }

    public List<ItemsSpec> getItemSpecList() {
        return itemSpecList;
    }

    public void setItemSpecList(List<ItemsSpec> itemSpecList) {
        this.itemSpecList = itemSpecList;
    }

    public ItemsParam getItemParams() {
        return itemParams;
    }

    public void setItemParams(ItemsParam itemParams) {
        this.itemParams = itemParams;
    }
}
