package com.researchecosystems.contentserviceapi.service;

import com.researchecosystems.contentserviceapi.entity.Invoice;
import com.researchecosystems.contentserviceapi.entity.Payment;
import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.model.request.Payment.CreatePaymentRequest;
import com.researchecosystems.contentserviceapi.model.response.PaymentResponse;
import com.researchecosystems.contentserviceapi.repository.InvoiceRepository;
import com.researchecosystems.contentserviceapi.repository.PaymentRepository;
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
