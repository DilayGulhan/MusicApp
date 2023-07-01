package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository< Payment ,String> {

}
