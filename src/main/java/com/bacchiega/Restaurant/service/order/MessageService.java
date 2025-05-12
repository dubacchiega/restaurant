package com.bacchiega.Restaurant.service.order;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private static String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");

    private static String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    @PostConstruct
    public void init(){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    protected static void sendMessage(String status, String phone) {
        Message message = Message
                .creator(
                        new PhoneNumber("whatsapp:" + phone),
                        new PhoneNumber("whatsapp:+14155238886"),
                        "Your order is " + status
                ).create();
    }
}
