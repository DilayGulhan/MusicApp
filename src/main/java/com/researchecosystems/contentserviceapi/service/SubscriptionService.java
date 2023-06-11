package com.researchecosystems.contentserviceapi.service;

import com.researchecosystems.contentserviceapi.entity.Subscription;
import com.researchecosystems.contentserviceapi.entity.User;
import com.researchecosystems.contentserviceapi.entity.UserRole;
import com.researchecosystems.contentserviceapi.exception.BusinessException;
import com.researchecosystems.contentserviceapi.exception.ErrorCode;
import com.researchecosystems.contentserviceapi.model.request.Subscription.CreateSubscriptionRequest;
import com.researchecosystems.contentserviceapi.model.request.Subscription.UpdateSubscriptionRequest;
import com.researchecosystems.contentserviceapi.model.response.SubscriptionResponse;
import com.researchecosystems.contentserviceapi.repository.SubscriptionRepository;
import com.researchecosystems.contentserviceapi.repository.UserRepository;
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
