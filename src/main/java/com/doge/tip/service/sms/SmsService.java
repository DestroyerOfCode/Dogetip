package com.doge.tip.service.sms;

import com.doge.tip.dto.sms.SmsRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private final SmsSender smsSender;

    public SmsService(TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

    public void sendSms(SmsRequestDTO dto) {
        smsSender.sendSms(dto);
    }
}
