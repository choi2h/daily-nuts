package com.dailynuts.subscription.service.portone;

import com.dailynuts.common.exception.CustomErrorCode;
import com.dailynuts.common.exception.CustomException;
import com.dailynuts.subscription.dto.PortOneTokenRequestDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class PortOneClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${portone.api-key}")
    private String apiKey;
    @Value("${portone.api-secret}")
    private String apiSecret;

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
        PortOneTokenRequestDto bodyDto = new PortOneTokenRequestDto(apiKey, apiSecret);


        String bodyAsJson;
        try {
            bodyAsJson = objectMapper.writeValueAsString(bodyDto); // <-- @JsonProperty 반영됨
        } catch (Exception e) {
            throw new CustomException(CustomErrorCode.PORTONE_CALL_FAILED);
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(bodyAsJson, headers);

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
