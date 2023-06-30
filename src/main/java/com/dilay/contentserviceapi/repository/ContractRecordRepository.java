package com.dilay.contentserviceapi.repository;

import com.dilay.contentserviceapi.entity.ContractRecord;
import com.dilay.contentserviceapi.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRecordRepository extends JpaRepository<ContractRecord, String> {
    public Optional<ContractRecord> findByName(String name);

}
