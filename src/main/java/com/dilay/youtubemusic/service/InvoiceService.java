package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.ContractRecord;
import com.dilay.youtubemusic.entity.Invoice;
import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.UserRole;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import com.dilay.youtubemusic.model.request.Payment.CreatePaymentRequest;
import com.dilay.youtubemusic.model.response.InvoiceResponse;
import com.dilay.youtubemusic.repository.ContractRecordRepository;
import com.dilay.youtubemusic.repository.InvoiceRepository;
import com.dilay.youtubemusic.repository.UserRepository;
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


