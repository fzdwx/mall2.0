package com.like.es.service.impl;

import com.like.es.constant.ES;
import com.like.es.pojo.ESRes;
import com.like.es.pojo.Items;
import com.like.es.service.ItemsSearchService;
import com.like.es.util.ESUtils;
import com.like.utils.PagedGridResult;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-09 15:34
 */
@Service
public class ItemsSearchServiceImpl implements ItemsSearchService {

    @Autowired
    private ESUtils esUtils;

    @Override
    public PagedGridResult searchItems(
            String keywords, String sort, Integer page, Integer pageSize) {
        // 1.构建查询条件
        SearchSourceBuilder sb = new SearchSourceBuilder();
//        sb.size(pageSize).from((page - 1) * ES.PAGE_SIZE);
        sb.query(QueryBuilders.matchQuery(Items.itemNameCol, keywords))
          .highlighter(new HighlightBuilder().field(Items.itemNameCol)
                                             .preTags(ES.preTag)
                                             .postTags(ES.postTag));
        // 2.查询
        ESRes esData = esUtils.queryDataBig(ES.ES_INDEX, sb);
        SearchHit[] searchHits = esData.getHits();

        // 3.封装返回条件
        List<Items> rows = Arrays.stream(searchHits).map(hit -> {
            HighlightField highInfo = (HighlightField) hit.getHighlightFields().values().toArray()[0];
            Map<String, Object> source = hit.getSourceAsMap();
            Items items = new Items();

            items.setItemId((String) source.get(Items.itemIdCol));
            items.setItemName(Arrays.toString(highInfo.getFragments()));
            items.setImgUrl((String) source.get(Items.imgUrlCol));
            items.setPrice((Integer) source.get(Items.priceCol));
            items.setSellCounts((Integer) source.get(Items.sellCountsCol));

            return items;
        }).collect(Collectors.toList());

        // 4.分页
        PagedGridResult res = new PagedGridResult();
        res.setRows(rows);
        res.setRecords(esData.getRecords());
        res.setTotal(esData.getTotal());
        res.setPage(page);
        return res;
    }
}
