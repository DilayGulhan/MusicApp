package com.dilay.youtubemusic.model.request.Subscription;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
