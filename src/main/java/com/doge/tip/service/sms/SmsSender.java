package com.doge.tip.service.sms;

import com.doge.tip.dto.sms.SmsRequestDTO;

public interface SmsSender {

    void sendSms(SmsRequestDTO smsRequest);
}
