package com.dilay.contentserviceapi.model.request.Subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubscriptionRequest {
    private double monthlyFee ;
}
