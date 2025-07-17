package com.dailynuts.subscription.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortOneTokenRequestDto {
    @JsonProperty("imp_key")
    private String impKey;

    @JsonProperty("imp_secret")
    private String impSecret;
}
