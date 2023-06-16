package com.researchecosystems.contentserviceapi.repository;

import com.researchecosystems.contentserviceapi.entity.ContractRecord;
import com.researchecosystems.contentserviceapi.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRecordRepository extends JpaRepository<ContractRecord, String> {
    public Optional<ContractRecord> findByName(String name);

}
