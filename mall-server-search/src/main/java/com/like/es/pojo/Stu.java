package com.like.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author like
 * @email 980650920@qq.com
 * @since 2021-03-08 16:46
 */
@Document(indexName = "stu")
@Data
public class Stu {

    @Field(store = true)
    private Integer age;
    @Field(store = true)
    private String name;
    @Id
    private Long stuId;
}
