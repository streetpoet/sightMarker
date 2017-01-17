package com.spstudio.rest;

import com.spstudio.entity.HtmlMarker;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

/**
 * Created by sp on 15/01/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SightPointControllerTest {

    private static RestTemplate restTemplate;

    @BeforeClass
    public static void init() {
        restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return true;
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                System.err.println(response.getStatusCode());
            }
        });
    }

    @Test
    public void sightQuery() throws Exception {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("http://localhost:8080/api/v1/sightIcon?east={east}&south={south}&west={west}&north={north}")
                .build()
                .expand("180", "-90", "-180", "90")
                .encode();
        URI uri = uriComponents.toUri();

        // header

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<?> requestEntity = new HttpEntity<>(headers);
        HttpEntity<List> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, List.class);
        List<HtmlMarker> list = response.getBody();
        System.out.println("marker size: " + list.size());
        Assert.assertEquals(26, list.size());
    }

    @Test
    public void deleteSightIcon() throws Exception {

    }

    @Test
    public void createSightIcon() throws Exception {

    }

}