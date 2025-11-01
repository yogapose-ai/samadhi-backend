package com.capstone.samadhi.record.repository;

import com.capstone.samadhi.record.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseStatus;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
}
