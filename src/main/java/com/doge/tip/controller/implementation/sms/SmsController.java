package com.doge.tip.controller.implementation.sms;

import com.doge.tip.controller.blueprint.sms.SmsControllerInterface;
import com.doge.tip.dto.sms.SmsRequestDTO;
import com.doge.tip.service.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/doge/sms")
public class SmsController implements SmsControllerInterface {

    private final SmsService smsService;

    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }

    @PostMapping(path = "/send")
    public void sendSms(@Valid @RequestBody SmsRequestDTO dto) {
        smsService.sendSms(dto);
    }
}
