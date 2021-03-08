package com.like.pojo.vo;

/**
 * @author like
 * @email 980650920@qq.com
 * @Description: 用于展示商品评价数量的VO
 * @since 2021-02-11 11:09
 */
public class CommentLevelCountsVO {

    private Long totalCounts;
    private Long goodCounts;
    private Long normalCounts;
    private Long badCounts;

    public CommentLevelCountsVO() {
    }

    public CommentLevelCountsVO(Long totalCounts, Long goodCounts, Long normalCounts, Long badCounts) {
        this.totalCounts = totalCounts;
        this.goodCounts = goodCounts;
        this.normalCounts = normalCounts;
        this.badCounts = badCounts;
    }

    public Long getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Long totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Long getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Long goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Long getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Long normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Long getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Long badCounts) {
        this.badCounts = badCounts;
    }
}
