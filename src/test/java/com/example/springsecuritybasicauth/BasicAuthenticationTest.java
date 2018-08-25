package com.example.springsecuritybasicauth;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by mtumilowicz on 2018-08-24.
 */
public class BasicAuthenticationTest extends BaseIT {
    
    @Test
    public void health() {
        assertThat(restTemplate
                        .getForEntity(
                                createURLWithPort("/health"),
                                null)
                        .getStatusCode(),
                is(HttpStatus.OK));
    }

    @Test
    public void admin_noCredentials_unauthorized() {
        assertThat(restTemplate
                        .getForEntity(
                                createURLWithPort("/admin"),
                                null)
                        .getStatusCode(),
                is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void admin_unauthorized() {
        assertThat(restTemplate
                        .withBasicAuth("admin", String.valueOf(new char[]{'a','d','m','i','n'}))
                        .getForEntity(
                                createURLWithPort("/admin"),
                                null)
                        .getStatusCode(),
                is(HttpStatus.OK));
    }

    @Test
    public void user_noCredentials_unauthorized() {
        assertThat(restTemplate
                        .getForEntity(
                                createURLWithPort("/user"),
                                null)
                        .getStatusCode(),
                is(HttpStatus.UNAUTHORIZED));
    }

    @Test
    public void user_authorized() {
        assertThat(restTemplate
                        .withBasicAuth("user", String.valueOf(new char[]{'u','s','e','r'}))
                        .getForEntity(
                                createURLWithPort("/user"),
                                null)
                        .getStatusCode(),
                is(HttpStatus.OK));
    }
}