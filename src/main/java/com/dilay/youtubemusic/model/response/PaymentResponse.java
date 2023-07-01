package com.dilay.youtubemusic.model.response;

import com.dilay.youtubemusic.entity.Invoice;
import com.dilay.youtubemusic.entity.Payment;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class PaymentResponse {
    private String id ;
    private double amount ;
    private String invoiceId ;
    private String sender_Card;
    private String receiver_Card ;


    public static PaymentResponse fromEntity(Payment payment , Invoice invoice) {
        return PaymentResponse.builder().
                id(payment.getId()).
                amount(invoice.getFee()).
                invoiceId(invoice.getId()).
                sender_Card(payment.getSenderCard()).
                receiver_Card("MUSIC APP COMPANY CARD ").build();


    }
}
