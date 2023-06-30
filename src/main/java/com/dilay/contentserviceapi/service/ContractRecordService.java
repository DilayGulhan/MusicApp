package com.dilay.contentserviceapi.service;

import com.dilay.contentserviceapi.entity.ContractRecord;
import com.dilay.contentserviceapi.entity.Invoice;
import com.dilay.contentserviceapi.entity.Subscription;
import com.dilay.contentserviceapi.entity.User;
import com.dilay.contentserviceapi.exception.BusinessException;
import com.dilay.contentserviceapi.exception.ErrorCode;
import com.dilay.contentserviceapi.model.request.contractrecord.UpdateContractRecordRequest;
import com.dilay.contentserviceapi.model.response.ContractRecordResponse;
import com.dilay.contentserviceapi.model.response.InvoiceResponse;
import com.dilay.contentserviceapi.model.response.SubscriptionResponse;
import com.dilay.contentserviceapi.repository.ContractRecordRepository;
import com.dilay.contentserviceapi.repository.InvoiceRepository;
import com.dilay.contentserviceapi.repository.SubscriptionRepository;
import com.dilay.contentserviceapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Data
@AllArgsConstructor

public class ContractRecordService {
private final SubscriptionRepository subscriptionRepository;

private final UserRepository userRepository;
private final ContractRecordRepository contractRecordRepository;
    private final InvoiceRepository invoiceRepository;
    private final AuthenticationService authenticationService;


public ContractRecordResponse addContractRecord(String authenticatedUserId , String subscriptionName){
    User currentUser = userRepository.findById(authenticatedUserId)
            .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));
    Subscription subscription = subscriptionRepository.findByName(subscriptionName)
            .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no subscription like that!"));


    ContractRecord record = new ContractRecord();
    record.setActive( subscription.isActive());
    record.setName(subscription.getName());
    record.setMonthlyFee(subscription.getMonthlyFee());
    record.setDuration(subscription.getDuration());
    record.setUserId(authenticatedUserId);
    currentUser.setSubscription(subscription);

    Invoice invoice = new Invoice();
    invoice.setContractRecordId(record.getId());
    invoice.setFee(record.getMonthlyFee());
    invoice.setPaid(false);

    record.setInvoice(invoice);
    currentUser.setInvoice(invoice);

    invoiceRepository.save(invoice);



    contractRecordRepository.save(record);
    invoice.setContractRecordId(record.getId());
    userRepository.save(currentUser);

    return ContractRecordResponse.fromEntity(currentUser , subscription , record);
}
public ContractRecordResponse updateContractRecord(String authenticatedUserId , UpdateContractRecordRequest contractRecordRequest){

    Subscription newRecordRequest = subscriptionRepository.findByName(contractRecordRequest.getName())
            .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no record like that!"));

    User currentUser = userRepository.findById(authenticatedUserId)
            .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

    Subscription subscriptionOld = currentUser.getSubscription();
    ContractRecord contractRecord = contractRecordRepository.findByName(subscriptionOld.getName())
            .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no old record like that!"));

    if(subscriptionOld.getDuration() >= newRecordRequest.getDuration()){
        throw new BusinessException(ErrorCode.forbidden , "You can only upgrade your subscription ");
    }

    contractRecord.setName(newRecordRequest.getName());
    contractRecord.setDuration(newRecordRequest.getDuration());
    contractRecord.setMonthlyFee(newRecordRequest.getMonthlyFee());


    currentUser.setSubscription(newRecordRequest);

    Invoice invoice = invoiceRepository.findByContractRecordId(contractRecord.getId())
            .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no invoice like that!"));
    invoice.setFee(contractRecord.getMonthlyFee());

    invoiceRepository.save(invoice);
    currentUser.setInvoice(invoice);
    userRepository.save(currentUser);
    contractRecordRepository.save(contractRecord);
    return ContractRecordResponse.fromEntity(currentUser , newRecordRequest , contractRecord );


}




}
