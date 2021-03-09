package com.like.es.pojo;

import lombok.Data;
import org.elasticsearch.search.SearchHit;

@Data
public class ESRes {
    private SearchHit[] hits;       // 每行显示的内容
    private long records;        // 总记录数
    private int total;            // 总页数
}