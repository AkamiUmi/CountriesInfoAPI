package uz.akamiumi.configs;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Configuration
public class RestClientConfig {

    @Primary
    @Bean("rest")
    public RestTemplate restTemplate(final RestTemplateBuilder rest) {
        log.info("[REST-TEMPLATE] start");
        return rest
                .interceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()))
                .requestFactory(() -> new BufferingClientHttpRequestFactory(clientHttpRequestFactory(10000)))
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory(final int timeout) {
        final RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
        final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(20);
        final CloseableHttpClient client = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsClientHttpRequestFactory(client);
    }
}