package vinay.mtlshttpclient.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vinay.mtlshttpclient.client.JavaHttpClient;

@RestController
@RequestMapping("/mtls")
@Slf4j
public class TestController {

    @Autowired
    JavaHttpClient javaHttpClient;

    @GetMapping("/java-http")
    public String javaClientTest() {
        return javaHttpClient.sendPostMessage(null, "http://localhost:8085/test", null);
    }

}
