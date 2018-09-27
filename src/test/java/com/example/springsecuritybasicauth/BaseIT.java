package com.example.springsecuritybasicauth;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by mtumilowicz on 2018-08-24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Ignore
class BaseIT {

    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
