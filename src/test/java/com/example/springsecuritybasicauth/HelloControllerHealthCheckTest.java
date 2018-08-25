package com.example.springsecuritybasicauth;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by mtumilowicz on 2018-08-24.
 */
public class HelloControllerHealthCheckTest extends BaseIT {
    
    @Test
    public void health() {
        assertThat(restTemplate
                        .getForEntity(
                                createURLWithPort("/health"),
                                null)
                        .getStatusCode(),
                is(HttpStatus.OK));
    }
}