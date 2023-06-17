package com.researchecosystems.contentserviceapi.repository;

import com.researchecosystems.contentserviceapi.entity.ContractRecord;
import com.researchecosystems.contentserviceapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice ,  String> {
    public Optional<Invoice> findByContractRecordId(String id);
}
