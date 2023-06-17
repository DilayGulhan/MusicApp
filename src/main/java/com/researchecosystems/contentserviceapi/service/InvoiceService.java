package com.researchecosystems.contentserviceapi.service;

import com.researchecosystems.contentserviceapi.entity.ContractRecord;
import com.researchecosystems.contentserviceapi.entity.Invoice;
import com.researchecosystems.contentserviceapi.entity.User;
import com.researchecosystems.contentserviceapi.entity.UserRole;
import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.model.request.Payment.CreatePaymentRequest;
import com.researchecosystems.contentserviceapi.model.response.CategoryResponse;
import com.researchecosystems.contentserviceapi.model.response.InvoiceResponse;
import com.researchecosystems.contentserviceapi.repository.ContractRecordRepository;
import com.researchecosystems.contentserviceapi.repository.InvoiceRepository;
import com.researchecosystems.contentserviceapi.repository.UserRepository;
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


