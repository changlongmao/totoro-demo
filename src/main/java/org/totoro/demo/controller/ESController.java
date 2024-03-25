package org.totoro.demo.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cat.IndicesResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.IndexSettings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.totoro.common.javabean.dto.BaseRespDTO;
import org.totoro.common.javabean.reqDto.IdStringReqDTO;
import org.totoro.common.javabean.vo.PageVO;
import org.totoro.common.util.StringUtil;
import org.totoro.demo.entity.UserEntity;
import org.totoro.demo.javabean.dto.UserPageReqDTO;
import org.totoro.demo.javabean.dto.UserReqDTO;
import org.totoro.demo.javabean.vo.UserVO;
import org.totoro.demo.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 用户表Controller层
 *
 * @author ChangLF 2023/07/28
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class ESController {

    @Resource
    private ElasticsearchClient elasticsearchClient;


    @PostMapping("/saveES")
    public BaseRespDTO<Void> saveES() throws IOException {

        // 创建、删除索引和查看所有索引信息
        //创建索引
        CreateIndexResponse indexRequest = elasticsearchClient.indices().create(createIndexBuilder ->
                createIndexBuilder.index("rest_client_index_9")
                        .settings(indexSettingsBuilder -> indexSettingsBuilder.numberOfReplicas("1").numberOfShards("2"))
                        .mappings(typeMappingBuilder -> typeMappingBuilder
                                .properties("age", propertyBuilder -> propertyBuilder.integer(integerNumberPropertyBuilder -> integerNumberPropertyBuilder))
                                .properties("name", propertyBuilder -> propertyBuilder.keyword(keywordPropertyBuilder -> keywordPropertyBuilder))
                                .properties("poems", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word")))
                                .properties("about", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word")))
                                .properties("success", propertyBuilder -> propertyBuilder.text(textPropertyBuilder -> textPropertyBuilder.analyzer("ik_max_word")))));
        //检查“indexRequest”请求的操作是否已被Elasticsearch集群确认
        boolean acknowledged = indexRequest.acknowledged();
        log.info("Index document successfully! {}", acknowledged);

        //查看所有索引信息（health status index uuid pri rep）
        IndicesResponse indicesResponse = elasticsearchClient.cat().indices();
        indicesResponse.valueBody().forEach(info -> System.out.println(info.health() + "\t" + info.status() + "\t" + info.index() + "\t" + info.uuid() + "\t" + info.pri() + "\t" + info.rep()));

        return BaseRespDTO.success();
    }

    @PostMapping("/saveDocument")
    public BaseRespDTO<Void> saveDocument() throws IOException {

        UserEntity userEntity = new UserEntity();
        String id = StringUtil.getUUID();
        log.info("id: {}", id);
        userEntity.setId(id);
        userEntity.setUsername("bb");
        // 创建、删除索引和查看所有索引信息
        // 创建索引
        IndexResponse clientIndex10 = elasticsearchClient.index(i ->
                i.index("rest_client_index_10").id(userEntity.getId()).document(userEntity));
        log.info("result: {}", clientIndex10.result());
        return BaseRespDTO.success();
    }

    @PostMapping("/getDocument")
    public BaseRespDTO<Void> getDocument() throws IOException {

        GetResponse<UserEntity> response = elasticsearchClient.get(g ->
                g.index("rest_client_index_10").id("8bcc84f0ea6746948d05fc79c7236c54"), UserEntity.class);
        if (response.found()) {
            UserEntity userEntity = response.source();
            log.info("Product name " + userEntity);
        } else {
            log.info("Product not found");
        }
        return BaseRespDTO.success();
    }

    @PostMapping("/searchDocument")
    public BaseRespDTO<Void> searchDocument() throws IOException {
        String searchText = "aa";
        SearchResponse<UserEntity> response = elasticsearchClient.search(s -> s
                        .index("rest_client_index_10")
                        .query(q -> q
                                .match(t -> t
                                        .field("username")
                                        .query(searchText)
                                )
                        ),
                UserEntity.class
        );
        log.info("search result :" + response.hits().hits());
        return BaseRespDTO.success();
    }

    @PostMapping("/updateDocument")
    public BaseRespDTO<Void> updateDocument() throws IOException {
        UserEntity userEntity = new UserEntity();
        String id = "8bcc84f0ea6746948d05fc79c7236c54";
        log.info("id: {}", id);
        userEntity.setId(id);
        userEntity.setUsername("cc");

        UpdateResponse<UserEntity> response = elasticsearchClient.update(u -> u
                        .index("rest_client_index_10")
                        .id("8bcc84f0ea6746948d05fc79c7236c54")
                        .upsert(userEntity),
                UserEntity.class
        );
        log.info("search result :" + response.result());
        return BaseRespDTO.success();
    }

}
