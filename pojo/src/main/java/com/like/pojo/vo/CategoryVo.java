package com.like.pojo.vo;

import java.util.List;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 二级分类
 * @since 2021-02-10 17:37
 */
public class CategoryVo {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;

    private List<SubCategoryVo> subCategoryList;

    public CategoryVo() {
    }

    public CategoryVo(Integer id, String name, String type, Integer fatherId, List<SubCategoryVo> subCategoryList) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.fatherId = fatherId;
        this.subCategoryList = subCategoryList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public List<SubCategoryVo> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(List<SubCategoryVo> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }
}
