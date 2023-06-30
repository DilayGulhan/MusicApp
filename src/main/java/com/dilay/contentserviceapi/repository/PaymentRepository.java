package com.dilay.contentserviceapi.repository;

import com.dilay.contentserviceapi.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository< Payment ,String> {

}
