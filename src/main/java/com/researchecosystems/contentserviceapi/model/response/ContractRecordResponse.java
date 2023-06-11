package com.researchecosystems.contentserviceapi.model.response;

import com.researchecosystems.contentserviceapi.entity.ContractRecord;
import com.researchecosystems.contentserviceapi.entity.Subscription;
import com.researchecosystems.contentserviceapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractRecordResponse {
    private String id ;
    private String name ;
    private double monthlyFee;
    private int duration ;
    private boolean isActive ;
    private String userId;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;

    public static ContractRecordResponse fromEntity(User user , Subscription subscription , ContractRecord contractRecord){
        return ContractRecordResponse.builder().
                id(subscription.getId()).
                name(subscription.getName()).
                monthlyFee(subscription.getMonthlyFee()).
                duration(subscription.getDuration()).
                isActive(subscription.isActive() && contractRecord.isActive()).
                userId(user.getId()).
                createdAt(contractRecord.getCreated()).
                updatedAt(contractRecord.getUpdated()).
                build();




    }

}
