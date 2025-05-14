package com.bacchiega.Restaurant.service.order;

import com.bacchiega.Restaurant.enums.OrderStatus;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Value("${twilio.account}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth}")
    private String AUTH_TOKEN;

//    @PostConstruct
//    public void init(){
//
//    }

    public void sendMessage(OrderStatus status, String phone) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message
                .creator(
                        new PhoneNumber("whatsapp:" + phone),
                        new PhoneNumber("whatsapp:+14155238886"),
                        "Your order is " + status
                ).create();
    }
}
