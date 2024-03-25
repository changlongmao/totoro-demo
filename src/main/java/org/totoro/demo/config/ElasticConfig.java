package org.totoro.demo.config;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.TransportUtils;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import java.util.stream.Stream;

/**
 * @author ChangLF 2024/02/20
 */
@Slf4j
@Configuration
public class ElasticConfig {

    @Resource
    private ElasticProperties elasticProperties;

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticsearchTransport transport) {
        // 创建 API 客户端
        return new ElasticsearchClient(transport);
    }

    @Bean
    public ElasticsearchAsyncClient elasticsearchAsyncClient(ElasticsearchTransport transport) {
        // 创建 API 客户端
        return new ElasticsearchAsyncClient(transport);
    }

    @Bean
    public ElasticsearchTransport transport() {
        // 加载SSL证书
        SSLContext sslContext = TransportUtils.sslContextFromHttpCaCrt(this.getClass().getResourceAsStream(elasticProperties.getSslKeyStore()));
        // 通过builder创建rest client，配置http client的HttpClientConfigCallback。
        HttpHost[] hosts = Stream.of(elasticProperties.getUris().split(",")).map(HttpHost::create).toArray(HttpHost[]::new);
        RestClient restClient = RestClient.builder(hosts)
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + elasticProperties.getApiKey())
                })
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setMaxConnTotal(500)
                                .setMaxConnPerRoute(300)
                                .setSSLContext(sslContext)
                                .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                ).build();

        // 使用 Jackson 映射器创建传输
        return new RestClientTransport(restClient, new JacksonJsonpMapper());
    }
}
