package com.doge.tip.controller.blueprint.sms;

import com.doge.tip.dto.sms.SmsRequestDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/doge/sms")
public interface SmsControllerInterface {

    @PostMapping("/send")
    void sendSms(@Valid @RequestBody SmsRequestDTO dto);
}
