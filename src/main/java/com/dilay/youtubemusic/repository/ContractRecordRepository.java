package com.dilay.youtubemusic.repository;

import com.dilay.youtubemusic.entity.ContractRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractRecordRepository extends JpaRepository<ContractRecord, String> {
    public Optional<ContractRecord> findByName(String name);

}
