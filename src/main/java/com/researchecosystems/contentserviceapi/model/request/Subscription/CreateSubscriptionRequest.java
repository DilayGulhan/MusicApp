package com.researchecosystems.contentserviceapi.model.request.Subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Negative;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriptionRequest {
    private String name ;
    private double monthlyFee ;
    private int duration ;
    private boolean isActive ;


}
