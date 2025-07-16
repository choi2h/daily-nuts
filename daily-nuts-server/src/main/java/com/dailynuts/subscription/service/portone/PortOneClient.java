package com.dailynuts.subscription.service.portone;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class PortOneClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String API_KEY = "channel-key-94d1ff2a-f5b3-46a9-8a5e-c821d447f49f";
    private static final String API_SECRET = "SU5JTElURV9UUklQTEVERVNfS0VZU1RS";

    private static final String TOKEN_URL = "https://api.iamport.kr/users/getToken";
    private static final String PAYMENT_LOOKUP_URL = "https://api.iamport.kr/payments/";

    public PortOnePaymentInfo getPaymentInfo(String impUid) {
        String accessToken = getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                PAYMENT_LOOKUP_URL + impUid,
                HttpMethod.GET,
                entity,
                JsonNode.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(CustomErrorCode.PORTONE_CALL_FAILED);
        }

        JsonNode responseBody = response.getBody().get("response");
        try {
            return objectMapper.treeToValue(responseBody, PortOnePaymentInfo.class);
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.PORTONE_CALL_FAILED);
        }
    }

    private String getAccessToken() {
        Map<String, String> body = new HashMap<>();
        body.put("imp_key", API_KEY);
        body.put("imp_secret", API_SECRET);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<JsonNode> response = restTemplate.postForEntity(TOKEN_URL, entity, JsonNode.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(CustomErrorCode.PORTONE_CALL_FAILED);
        }

        try {
            return response.getBody().get("response").get("access_token").asText();
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.PORTONE_CALL_FAILED);
        }
    }
}
