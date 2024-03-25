package org.totoro.demo;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.totoro.common.javabean.dto.BaseRespDTO;
import org.totoro.common.util.StringUtil;
import org.totoro.demo.entity.UserEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author ChangLF 2024/02/26
 */
@Slf4j
@SpringBootTest
public class ElasticTest {

    @Resource
    private ElasticsearchClient elasticsearchClient;

    @Resource
    private ElasticsearchAsyncClient asyncClient;

    @Test
    public void saveES() throws IOException {

        // 创建、删除索引和查看所有索引信息
        //创建索引
        CreateIndexResponse indexRequest = elasticsearchClient.indices().create(createIndexBuilder ->
                createIndexBuilder.index("rest_client_index_11")
                        .settings(indexSettingsBuilder -> indexSettingsBuilder.numberOfReplicas("1").numberOfShards("2"))
                        .mappings(typeMappingBuilder -> typeMappingBuilder
                                .properties("age", propertyBuilder -> propertyBuilder.integer(integerNumberPropertyBuilder -> integerNumberPropertyBuilder))
                                .properties("name", propertyBuilder -> propertyBuilder.keyword(keywordPropertyBuilder -> keywordPropertyBuilder.index(true)))
                                .properties("poems", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word")))
                                .properties("about", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word")))
                                .properties("date", propertyBuilder -> propertyBuilder.date(textPropertyBuilder -> textPropertyBuilder.format("yyyy-MM-dd")))
                                .properties("success", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word"))))
                        .aliases("rest_client_index", index -> index.isWriteIndex(false)));
        //检查“indexRequest”请求的操作是否已被Elasticsearch集群确认
        boolean acknowledged = indexRequest.acknowledged();
        log.info("Index document successfully! {}", acknowledged);

        //查看所有索引信息（health status index uuid pri rep）
        IndicesResponse indicesResponse = elasticsearchClient.cat().indices();
        indicesResponse.valueBody().forEach(info -> System.out.println(info.health() + "\t" + info.status() + "\t" + info.index() + "\t" + info.uuid() + "\t" + info.pri() + "\t" + info.rep()));

    }
    @Test
    public void saveDocument() throws IOException {

        UserEntity userEntity = new UserEntity();
        String id = StringUtil.getUUID();
        log.info("id: {}", id);
        userEntity.setId(id);
        userEntity.setUsername("ee");
        userEntity.setPassword("ee");
        userEntity.setRearName("ee");
        // 创建、删除索引和查看所有索引信息
        // 创建索引
        IndexResponse clientIndex10 = elasticsearchClient.index(i ->
                i.index("rest_client_index_10").id(userEntity.getId()).document(userEntity));
        log.info("result: {}", clientIndex10.result());
    }

    @Test
    public void saveDocument2() throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("id", StringUtil.getUUID());
        map.put("age", 11);
        map.put("name", "口红红酒啊哈哈哈红酒");
        map.put("success", "喝红牛就很牛");
        // 创建、删除索引和查看所有索引信息
        // 创建索引
        IndexResponse clientIndex10 = elasticsearchClient.index(i ->
                i.index("rest_client_index_12").document(map));
        log.info("result: {}", clientIndex10.result());
    }

    @Test
    public void getDocument() throws IOException {

        GetResponse<UserEntity> response = elasticsearchClient.get(g ->
                g.index("rest_client_index_10").id("d02e9afe62ed45659a55094f6d5a751a"), UserEntity.class);
        if (response.found()) {
            UserEntity userEntity = response.source();
            log.info("Product name " + userEntity);
        } else {
            log.info("Product not found");
        }
    }

    @Test
    public void searchDocument() throws IOException {
        String searchText = "d02e9afe62ed45659a55094f6d5a751a";
        SearchResponse<HashMap> response = elasticsearchClient.search(s -> s
                        .index("rest_client_index_10")
                        .query(q -> q
                                .match(t -> t
                                        .field("id")
                                        .query(searchText)
                                )
                        ),
                HashMap.class
        );
        log.info("search result :" + response.hits().hits());
    }

    @Test
    public void updateDocument() throws IOException {
        UserEntity userEntity = new UserEntity();
        String id = "5b2ce888c67c4db6af6609d2c6bb4ff4";
        log.info("id: {}", id);
        userEntity.setId(id);
        userEntity.setUsername("cc");

        UpdateResponse<UserEntity> response = elasticsearchClient.update(u -> u
                        .index("rest_client_index_10")
                        .id("5b2ce888c67c4db6af6609d2c6bb4ff4")
                        .doc(userEntity), UserEntity.class
        );
        log.info("search result :" + response.result());
    }

    @Test
    public void deleteDocument() throws IOException {

        DeleteResponse deleteResponse = elasticsearchClient.delete(u -> u
                .index("rest_client_index_10")
                .id("5b2ce888c67c4db6af6609d2c6bb4ff4"));
        log.info("search result :" + deleteResponse.result());
    }

    @Test
    public void asyncSearchDocument() throws IOException {
        CompletableFuture<GetResponse<UserEntity>> completableFuture = asyncClient.get(g ->
                g.index("rest_client_index_10").id("e5e4e239d6da4ddd965eae93b290dace"), UserEntity.class);
        completableFuture.whenComplete(((userEntityGetResponse, throwable) -> {
            if (throwable != null) {
                log.error("search result failed, error message: ", throwable);
            } else {
                log.info("search result :" + userEntityGetResponse.source());
            }
        }));
    }
}
