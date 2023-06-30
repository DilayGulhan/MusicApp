package com.dilay.contentserviceapi.repository;

import com.dilay.contentserviceapi.entity.ContractRecord;
import com.dilay.contentserviceapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice ,  String> {
    public Optional<Invoice> findByContractRecordId(String id);
}
