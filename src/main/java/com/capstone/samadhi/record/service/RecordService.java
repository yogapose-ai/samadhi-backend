package com.capstone.samadhi.record.service;

import com.capstone.samadhi.common.ResponseDto;
import com.capstone.samadhi.record.dto.RecordRequest;
import com.capstone.samadhi.record.dto.RecordResponse;
import com.capstone.samadhi.record.entity.Record;
import com.capstone.samadhi.record.repository.RecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

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

    public ResponseDto<RecordResponse> findById(Long id) {
        Record record = recordRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Record not found"));
        /*
        * user 에러처리
        * if(!record.getUser().getId().equals()){
        *   throw new AccessDeniedException("접근 권한이 없습니다.");
        * }
        * */
        RecordResponse response = RecordResponse.from(record);
        return new ResponseDto<>(true, response);
    }
//    public ResponseDto<List<RecordResponse>> findByUser(Pageable pageable) {
//
//        User user = userService.findUserByEmail(userEmail)
//                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + userEmail));
//
//        Page<Record> recordPage = recordRepository.findByUser(user, pageable);
//
//        List<RecordResponse> responseList = recordPage.getContent().stream()
//                .map(RecordResponse::from)
//                .collect(Collectors.toList());
//
//        return new ResponseDto<>(true, responseList);
//    }
}
