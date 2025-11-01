package com.capstone.samadhi.record.service;

import com.capstone.samadhi.common.ResponseDto;
import com.capstone.samadhi.record.dto.RecordRequest;
import com.capstone.samadhi.record.dto.RecordResponse;
import com.capstone.samadhi.record.entity.Record;
import com.capstone.samadhi.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class RecordService {
    private final RecordRepository recordRepository;

    @Transactional
    public ResponseDto<RecordResponse> save(RecordRequest request) {
        Record record = request.toEntity();
        Record savedRecord = recordRepository.save(record);
        RecordResponse response = RecordResponse.from(savedRecord);
        return new ResponseDto<>(true, response);
    }

}
