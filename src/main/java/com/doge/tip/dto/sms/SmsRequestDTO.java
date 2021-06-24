package com.doge.tip.dto.sms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SmsRequestDTO {

    @NotBlank
    private final String phoneNumber; // destination

    @NotBlank
    private final String message;

    public SmsRequestDTO(@JsonProperty("phoneNumber") String phoneNumber,
                      @JsonProperty("message") String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber= ..." + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
