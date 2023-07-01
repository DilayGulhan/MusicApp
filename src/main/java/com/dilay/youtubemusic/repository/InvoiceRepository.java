package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice ,  String> {
    public Optional<Invoice> findByContractRecordId(String id);
}
