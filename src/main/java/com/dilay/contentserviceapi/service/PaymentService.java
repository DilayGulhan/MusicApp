package com.dilay.contentserviceapi.service;

import com.dilay.contentserviceapi.entity.Invoice;
import com.dilay.contentserviceapi.entity.Payment;
import com.dilay.contentserviceapi.exception.BusinessException;
import com.dilay.contentserviceapi.exception.ErrorCode;
import com.dilay.contentserviceapi.model.request.Payment.CreatePaymentRequest;
import com.dilay.contentserviceapi.model.response.PaymentResponse;
import com.dilay.contentserviceapi.repository.InvoiceRepository;
import com.dilay.contentserviceapi.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;


    public void addPayment(CreatePaymentRequest payment ,String invoiceId){
        Invoice invoice =invoiceRepository.findById(invoiceId)
                .orElseThrow(() ->
                        new BusinessException(ErrorCode.resource_missing, "There is no invoice like that!"));

        Payment pay = new Payment();
        pay.setAmount(invoice.getFee());
        pay.setInvoiceId(invoiceId);
        pay.setSenderCard(payment.getSenderCard());
        pay.setReceiverCard("MUSIC APP COMPANY CARD ");
        paymentRepository.save(pay);
    }
}
