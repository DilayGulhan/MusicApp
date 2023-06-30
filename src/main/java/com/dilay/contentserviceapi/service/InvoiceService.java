package com.dilay.contentserviceapi.service;

import com.dilay.contentserviceapi.entity.ContractRecord;
import com.dilay.contentserviceapi.entity.Invoice;
import com.dilay.contentserviceapi.entity.User;
import com.dilay.contentserviceapi.entity.UserRole;
import com.dilay.contentserviceapi.exception.BusinessException;
import com.dilay.contentserviceapi.exception.ErrorCode;
import com.dilay.contentserviceapi.model.request.Payment.CreatePaymentRequest;
import com.dilay.contentserviceapi.model.response.CategoryResponse;
import com.dilay.contentserviceapi.model.response.InvoiceResponse;
import com.dilay.contentserviceapi.repository.ContractRecordRepository;
import com.dilay.contentserviceapi.repository.InvoiceRepository;
import com.dilay.contentserviceapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor
public class InvoiceService {
    private final UserRepository userRepository;
    private final ContractRecordRepository contractRecordRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentService paymentService;

    public Page<InvoiceResponse> listInvoice(Pageable pageable , String authenticatedUserId) {
    determineWhetherAdmin(authenticatedUserId);
        return invoiceRepository.findAll(pageable).map(InvoiceResponse::fromEntity);

    }

    public InvoiceResponse getInvoiceByAdmin( String invoiceId ,String authenticatedUserId ){
     determineWhetherAdmin(authenticatedUserId);
        Invoice invoice  = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no invoice like that!"));

        return InvoiceResponse.fromEntity(invoice);
    }


    public InvoiceResponse payInvoice(CreatePaymentRequest paymentRequest, String authenticatedUserId, String contractRecordId ){
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        ContractRecord contractRecord = contractRecordRepository.findById(contractRecordId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no record like that!"));



        if(contractRecord.getUserId().equals(currentUser.getId())){
            paymentService.addPayment(paymentRequest , currentUser.getInvoice().getId());
            currentUser.getInvoice().setPaid(true);
            invoiceRepository.save(currentUser.getInvoice());
            return InvoiceResponse.fromEntity(currentUser.getInvoice());
        }

        else {
            throw new BusinessException(ErrorCode.resource_missing, "You can't reach other's invoice!");
        }
    }

    public void determineWhetherAdmin(  String authenticatedUserId){
        User currentUser = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

        if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
            throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
        }
    }





}


