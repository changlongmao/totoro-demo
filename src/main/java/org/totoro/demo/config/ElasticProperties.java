package org.totoro.demo.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * elasticsearch配置
 * @author changlf 2024-02-24
 */
@Slf4j
@Data
@ConfigurationProperties("spring.elasticsearch")
@Component
public class ElasticProperties {

    /**
     * url地址，多个英文逗号隔开
     */
    private String uris;

    /**
     * apiKey
     */
    private String apiKey;

    /**
     * ssl密钥文件位置
     */
    private String sslKeyStore;

}