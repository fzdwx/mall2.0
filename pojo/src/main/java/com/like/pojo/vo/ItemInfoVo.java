package com.like.pojo.vo;

import com.like.pojo.Items;
import com.like.pojo.ItemsImg;
import com.like.pojo.ItemsParam;
import com.like.pojo.ItemsSpec;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-02-11 10:48
 */
public class ItemInfoVo {
    private Items item;
    private List<ItemsImg> imgs;
    private List<ItemsSpec> specs;
    private ItemsParam param;

    public ItemInfoVo(Items item, List<ItemsImg> imgs, List<ItemsSpec> specs, ItemsParam param) {
        this.item = item;
        this.imgs = imgs;
        this.specs = specs;
        this.param = param;
    }

    public ItemInfoVo() {
    }

    public Items getItem() {
        return item;
    }

    public void setItem(Items item) {
        this.item = item;
    }

    public List<ItemsImg> getImgs() {
        return imgs;
    }

    public void setImgs(List<ItemsImg> imgs) {
        this.imgs = imgs;
    }

    public List<ItemsSpec> getSpecs() {
        return specs;
    }

    public void setSpecs(List<ItemsSpec> specs) {
        this.specs = specs;
    }

    public ItemsParam getParam() {
        return param;
    }

    public void setParam(ItemsParam param) {
        this.param = param;
    }
}
