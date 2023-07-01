package com.dilay.youtubemusic.service;

import com.dilay.youtubemusic.entity.Subscription;
import com.dilay.youtubemusic.entity.User;
import com.dilay.youtubemusic.entity.UserRole;
import com.dilay.youtubemusic.exception.BusinessException;
import com.dilay.youtubemusic.exception.ErrorCode;
import com.dilay.youtubemusic.model.request.Subscription.CreateSubscriptionRequest;
import com.dilay.youtubemusic.model.request.Subscription.UpdateSubscriptionRequest;
import com.dilay.youtubemusic.model.response.SubscriptionResponse;
import com.dilay.youtubemusic.repository.SubscriptionRepository;
import com.dilay.youtubemusic.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SubscriptionService {
   private final SubscriptionRepository subscriptionRepository;
private final UserRepository userRepository ;


   public Page<SubscriptionResponse> listSubscription(Pageable pageable) {

      return subscriptionRepository.findAll(pageable).map(SubscriptionResponse::fromEntity);

   }

   public SubscriptionResponse addSubscription(CreateSubscriptionRequest subscriptionRequest ,String authenticatedUserId){
      User currentUser = userRepository.findById(authenticatedUserId)
              .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

      if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
         throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
      }


      Subscription subscription = new Subscription();
      subscription.setName(subscriptionRequest.getName());
      subscription.setDuration(subscriptionRequest.getDuration());
      subscription.setMonthlyFee(subscriptionRequest.getMonthlyFee());
      subscription.setActive(subscriptionRequest.isActive());
      subscriptionRepository.save(subscription);
      return SubscriptionResponse.fromEntity(subscription);
   }

   public SubscriptionResponse deleteSubscription(String subscriptionId ,String authenticatedUserId){
      User currentUser = userRepository.findById(authenticatedUserId)
              .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

      if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
         throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
      }

      Subscription subscription = subscriptionRepository.findById(subscriptionId)
              .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no subscription option like that!"));
      subscription.setActive(false);
      return SubscriptionResponse.fromEntity(subscription);
   }

   public SubscriptionResponse updateSubscription
           (UpdateSubscriptionRequest updateSubscriptionRequest , String subscriptionId ,String authenticatedUserId){
      User currentUser = userRepository.findById(authenticatedUserId)
              .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no user like that!"));

      if (!currentUser.getUserRole().equals(UserRole.ADMIN) ) {
         throw new BusinessException(ErrorCode.forbidden, "You dont have the privilege.");
      }

      Subscription subscription = subscriptionRepository.findById(subscriptionId)
              .orElseThrow(() -> new BusinessException(ErrorCode.resource_missing, "There is no Subscription option like that!"));
      subscription.setMonthlyFee(updateSubscriptionRequest.getMonthlyFee());

      return SubscriptionResponse.fromEntity(subscription);
   }

}
