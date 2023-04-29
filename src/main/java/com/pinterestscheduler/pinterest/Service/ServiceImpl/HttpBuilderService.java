package com.pinterestscheduler.pinterest.Service.ServiceImpl;

import com.pinterestscheduler.pinterest.Config.APIConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class HttpBuilderService<T> {

    public HttpEntity<T> buildHttpEntity(T request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(APIConfig.AUTHORIZATION, APIConfig.BEARER + APIConfig.ACCESS_TOKEN);
        return new HttpEntity<>(request, headers);
    }

    public HttpEntity<T> buildHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(APIConfig.AUTHORIZATION, APIConfig.BEARER + APIConfig.ACCESS_TOKEN);
        return new HttpEntity<>(headers);
    }
}
