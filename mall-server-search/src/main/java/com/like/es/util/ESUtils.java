package com.like.es.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.like.es.constant.ES;
import com.like.es.pojo.ESRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class ESUtils {

    @Autowired
    private RestHighLevelClient client;

    public long countLogs(String esIndex, SearchSourceBuilder builder) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);
        searchRequest.source(builder);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            return result.getHits().getTotalHits().value;
        } catch (Exception e) {
            log.error("error query data", e);
            return 0;
        }
    }

    /**
     * 根据id精确查询
     * @param esIndex
     * @param id
     * @return
     */
    public Map<String, Object> getOne(String esIndex, String id) {
        GetRequest getRequest = new GetRequest(esIndex, id);
        try {
            GetResponse getResponse = client.get(getRequest, RequestOptions.DEFAULT);
            if (getResponse.getSource() == null) {
                return null;
            }
            return getResponse.getSourceAsMap();
        } catch (Exception e) {
            log.warn("getOne from ES Exception : ", e);
            return null;
        }
    }

    public SearchHit[] queryDocsByIds(String esIndex, List<String> ids) {
        SearchSourceBuilder builder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        builder.query(QueryBuilders.termsQuery("_id", ids));
        searchRequest.indices(esIndex);
        searchRequest.source(builder);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            return result.getHits().getHits();
        } catch (Exception e) {
            log.error("Batch query docs by ids failed!", e);
        }
        return null;
    }

    public void insertOrUpdateOne(String index, String id, String data) {
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(data, XContentType.JSON);
        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("error save info", e);
        }
    }

    /**
     * 修改文档-只需要给要修改的字段
     */
    public void updateOne(String index, String id, Map<String, Object> map) {
        UpdateRequest updateRequest = new UpdateRequest(index, id);
        updateRequest.doc(map);
        try {
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            log.info("updateOne result:{}", JSONUtil.toJsonStr(updateResponse));
        } catch (Exception e) {
            log.warn("updateOne to ES Exception : ", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量插入文档
     * @param index ES索引
     * @param documents 待提交的批量文档
     * @param uuidKey 文档中ID字段对应的key值
     */
    public BulkResponse insertDocumentsAsBatch(String index, List<Map<String, Object>> documents, String uuidKey) {
        BulkResponse response = null;
        if (StringUtils.isBlank(index) || CollUtil.isEmpty(documents)) {
            log.warn("Es index is blank or documents is empty.");
            return response;
        }

        try {
            int size = documents.size();
            BulkRequest bulkRequest = new BulkRequest();
            for (int i = 0; i < size; i++) {
                Map<String, Object> document = documents.get(i);
                if (MapUtil.isEmpty(document) || !document.containsKey(uuidKey)) {
                    continue;
                }
                bulkRequest.add(new IndexRequest(index).opType("create").id(document.get(uuidKey).toString())
                                                       .source(document));
            }
            response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("Insert documents to es as batch failed!", e);
        }
        return response;
    }


    /**
     * 批量更新文档
     * @param index ES索引
     * @param documents 待提交的批量文档
     * @param uuidKey 文档中ID字段对应的key值
     */
    public BulkResponse updateDocumentsAsBatch(String index, List<Map<String, Object>> documents, String uuidKey) {
        BulkResponse response = null;
        if (StringUtils.isBlank(index) || CollUtil.isEmpty(documents)) {
            log.warn("Es index is blank or documents is empty.");
            return response;
        }

        try {
            int size = documents.size();
            BulkRequest bulkRequest = new BulkRequest();
            for (int i = 0; i < size; i++) {
                Map<String, Object> document = documents.get(i);
                if (MapUtil.isEmpty(document) || !document.containsKey(uuidKey)) {
                    continue;
                }
                bulkRequest.add(new UpdateRequest(index, document.get(uuidKey).toString()).doc(document));
            }
            response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("Update documents to es as batch failed!", e);
        }
        return response;
    }

    public SearchResponse queryData2(SearchSourceBuilder sourceBuilder, String... esIndex) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            return result;
        } catch (Exception e) {
            log.error("error query info", e);
        }
        return null;
    }

    /**
     * 判断索引名是否存在
     * @param indexName
     * @param
     * @return
     */
    public boolean isExistsIndex(String indexName) {
        GetIndexRequest request = new GetIndexRequest(indexName);
        try {
            boolean response = client.indices().exists(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            log.error("error", e);
            return false;
        }

    }

    public long counts4Index(String esIndex) {
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            return result.getHits().getTotalHits().value;
        } catch (Exception e) {
            log.error("error query data", e);
            return 0;
        }
    }

    public long countDistinctField(String esIndex, String countField, SearchSourceBuilder sourceBuilder) {
        long count = 0;
        if (StringUtils.isBlank(esIndex) || StringUtils.isBlank(countField)) {
            return count;
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);
        String identifier = UUID.randomUUID().toString();
        AggregationBuilder aggregationBuilder = AggregationBuilders.cardinality(identifier).field(countField);
        sourceBuilder.aggregation(aggregationBuilder);
        sourceBuilder.size(0);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            Histogram histogram = (Histogram) result.getAggregations().asMap().get(countField);
            long total_value = 0;
            for (Histogram.Bucket t : histogram.getBuckets()) {
                Cardinality cardinality = t.getAggregations().get(identifier);
                long value = cardinality.getValue();
                total_value = total_value + value;
            }
            return total_value;
        } catch (Exception e) {
            log.error("Count field failed!", e);
        }
        return 0;
    }

    public long countDistinctField(String esIndex, String countField) {
        long count = 0;
        if (StringUtils.isBlank(esIndex) || StringUtils.isBlank(countField)) {
            return count;
        }

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.cardinality("field_count").field(countField);
        sourceBuilder.aggregation(aggregationBuilder);
        sourceBuilder.size(0);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            Histogram histogram = (Histogram) result.getAggregations().asMap().get(countField);
            long total_value = 0;
            for (Histogram.Bucket t : histogram.getBuckets()) {
                Cardinality cardinality = t.getAggregations().get("field_count");
                long value = cardinality.getValue();
                total_value = total_value + value;
            }
            return total_value;
        } catch (Exception e) {
            log.error("Count field failed!", e);
        }
        return 0;
    }

    public boolean deleteData(String esIndexName, String esId) {
        org.elasticsearch.action.delete.DeleteRequest deleteRequest = new org.elasticsearch.action.delete.DeleteRequest(
                esIndexName, esId);
        org.elasticsearch.action.delete.DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            log.error("error is " + e);
            return false;

        }
        return true;
    }

    /**
     * 分组统计
     * @param esIndex 索引
     * @param groupFiled 被统计字段
     */
    public Map<String, Long> groupCount(String esIndex, String groupFiled) {
        Map<String, Long> statistics = new HashMap<>();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        try {
            sourceBuilder.size(0);
            searchRequest.source(sourceBuilder);

            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("fieldCount").field(groupFiled);
            sourceBuilder.aggregation(aggregationBuilder);

            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            Map<String, Aggregation> aggregationMap = result.getAggregations().asMap();
            ParsedStringTerms grageTerms = (ParsedStringTerms) aggregationMap.get("fieldCount");
            List buckets = grageTerms.getBuckets();

            for (Object object : buckets) {
                ParsedStringTerms.ParsedBucket obj = (ParsedStringTerms.ParsedBucket) object;
                String name = obj.getKeyAsString();
                long count = obj.getDocCount();
                statistics.putIfAbsent(name, count);
            }
        } catch (Exception e) {
            log.error("Group count failed!", e);
        }
        return statistics;
    }

    /**
     * 分组统计
     * @param esIndex 索引
     * @param groupFiled 被统计字段
     * @param boolQueryBuilder 查询条件
     */
    public Map<String, Long> groupCount(String esIndex, String groupFiled, BoolQueryBuilder boolQueryBuilder) {
        Map<String, Long> statistics = new HashMap<>();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        try {
            sourceBuilder.size(0);
            searchRequest.source(sourceBuilder);
            sourceBuilder.query(boolQueryBuilder);

            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("fieldCount").field(groupFiled);
            aggregationBuilder.order(BucketOrder.count(false));
            aggregationBuilder.size(10000);
            sourceBuilder.aggregation(aggregationBuilder);
            log.info("sourceBuilder:" + sourceBuilder.toString());

            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            Map<String, Aggregation> aggregationMap = result.getAggregations().asMap();
            ParsedStringTerms grageTerms = (ParsedStringTerms) aggregationMap.get("fieldCount");
            List buckets = grageTerms.getBuckets();

            for (Object object : buckets) {
                ParsedStringTerms.ParsedBucket obj = (ParsedStringTerms.ParsedBucket) object;
                String name = obj.getKeyAsString();
                long count = obj.getDocCount();
                statistics.putIfAbsent(name, count);
            }
        } catch (Exception e) {
            log.error("Group count failed!", e);
        }
        return statistics;
    }

    public ESRes queryDataBig(String esIndex, SearchSourceBuilder sourceBuilder) {
        ESRes res = new ESRes();
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(1));
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(esIndex);
        searchRequest.source(sourceBuilder);
        searchRequest.scroll(scroll);
        try {
            SearchResponse result = client.search(searchRequest, RequestOptions.DEFAULT);
            String scrollId = result.getScrollId();
            SearchHits h1 = result.getHits();
            SearchHit[] searchHits2 = h1.getHits();
            TotalHits h1t = h1.getTotalHits();
            res.setRecords(h1t.value);
            res.setTotal(Math.toIntExact(
                    h1t.value % ES.PAGE_SIZE == 0
                            ? h1t.value / ES.PAGE_SIZE
                            : (h1t.value / ES.PAGE_SIZE) + 1));

            List<SearchHit> searchHitList = new ArrayList<>();
            for (int i = 0; i < searchHits2.length; i++) {
                searchHitList.add(searchHits2[i]);
            }
            if (result.getHits().getTotalHits().value > 100000) {
                long count = searchHits2.length;
                while (count < 100000) {
                    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                    scrollRequest.scroll(scroll);
                    try {
                        result = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                    } catch (Exception e) {
                        log.error("error is " + e);
                    }
                    scrollId = result.getScrollId();
                    searchHits2 = result.getHits().getHits();
                    for (int i = 0; i < searchHits2.length; i++) {
                        searchHitList.add(searchHits2[i]);
                    }
                    long size = searchHits2.length;
                    count = count + size;

                }
                SearchHit[] searchHits = new SearchHit[searchHitList.size()];
                searchHitList.toArray(searchHits);

                res.setHits(searchHits);
                return res;
            } else {
                while (searchHits2 != null && searchHits2.length > 0) {
                    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                    scrollRequest.scroll(scroll);
                    try {
                        result = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                    } catch (Exception e) {
                        log.error("error is " + e);
                    }
                    scrollId = result.getScrollId();
                    searchHits2 = result.getHits().getHits();
                    for (int i = 0; i < searchHits2.length; i++) {
                        searchHitList.add(searchHits2[i]);
                    }
                }
                SearchHit[] searchHits = new SearchHit[searchHitList.size()];
                searchHitList.toArray(searchHits);
                res.setHits(searchHits);
                return res;
            }

        } catch (Exception e) {
            log.error("error query info", e);
        }
        return null;
    }
}
