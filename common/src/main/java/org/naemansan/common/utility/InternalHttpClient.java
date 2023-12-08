package org.naemansan.common.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class InternalHttpClient {
    private final RestTemplate restTemplate;

    public <T> Object sendGetRequest(
            String url,
            HttpEntity<?> request,
            Class<T> responseType
            ) throws Exception {

        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                responseType
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new Exception("Error while sending request");
        }
    }

    public <T> Object sendPostRequest(
            String url,
            HttpEntity<?> request,
            Class<T> responseType
    ) throws Exception {
        ResponseEntity<T> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                responseType
        );

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new Exception("Error while sending request");
        }
    }
}
