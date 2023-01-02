package vinay.mtlshttpclient.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;

@Component
@Slf4j
public class JavaHttpClient {

    HttpClient httpClient;

    @PostConstruct
    public void init() throws Exception {
        httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(60l))
                .sslContext(setSSLContext()).build();
    }

    public String sendPostMessage(String requestBody, String uri, Map<String, String> headers) {
        HttpRequest httpRequest = buildPostHttpRequest(requestBody, uri, headers);
        try {
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (Exception e) {
            log.error("error while calling client", e);
            return e.getMessage();
        }
    }

    private HttpRequest buildPostHttpRequest(String requestBody, String uri, Map<String, String> headers) {
        HttpRequest.BodyPublisher bodyPublisher = StringUtils.isBlank(requestBody) ? HttpRequest.BodyPublishers.noBody() :
                HttpRequest.BodyPublishers.ofString(requestBody);
        HttpRequest.Builder httpRequestBuilder = HttpRequest.newBuilder(URI.create(uri)).POST(bodyPublisher);
        if (!CollectionUtils.isEmpty(headers)) {
            headers.forEach((k, v) -> httpRequestBuilder.header(k, v));
        }
        return httpRequestBuilder.build();
    }

    private SSLContext setSSLContext() throws Exception {
        SSLContext sslContext = SSLContext.getInstance("TLS1.2");
        sslContext.init(null, null, null);
        return sslContext;
    }
}
