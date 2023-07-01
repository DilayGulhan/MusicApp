package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.ContractRecord;
import com.dilay.youtubemusic.entity.Invoice;
import com.dilay.youtubemusic.entity.Subscription;
import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import com.dilay.youtubemusic.model.request.contractrecord.UpdateContractRecordRequest;
import com.dilay.youtubemusic.model.response.ContractRecordResponse;
import com.dilay.youtubemusic.repository.ContractRecordRepository;
import com.dilay.youtubemusic.repository.InvoiceRepository;
import com.dilay.youtubemusic.repository.SubscriptionRepository;
import com.dilay.youtubemusic.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
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
