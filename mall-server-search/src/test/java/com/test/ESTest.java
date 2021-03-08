package com.test;

import cn.hutool.json.JSONUtil;
import com.like.es.SearchApplication;
import com.like.es.pojo.Stu;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 16:37
 */
@SpringBootTest(classes = SearchApplication.class)
@RunWith(SpringRunner.class)
public class ESTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void createIndex() throws IOException {

        CreateIndexRequest index = new CreateIndexRequest("1");

        Stu stu = new Stu();
        stu.setStuId(123L);
        stu.setName("like");
        stu.setAge(18);

        index.source(JSONUtil.toJsonStr(stu), XContentType.JSON);
        CreateIndexResponse resp = restHighLevelClient.indices().create(index, RequestOptions.DEFAULT);

    }

}
