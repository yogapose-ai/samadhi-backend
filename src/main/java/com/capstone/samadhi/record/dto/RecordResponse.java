package com.capstone.samadhi.record.dto;

import com.capstone.samadhi.record.entity.Record;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record RecordResponse(
        Long id,
        LocalDateTime dateTime,
        Duration workingout_time,
        String youtube_url,
        int total_score,
        List<TimeLineResponse> timelines
) {

    public static RecordResponse from(Record record) {

        List<TimeLineResponse> timelines = record.getTimeLineList().stream()
                .map(TimeLineResponse::from) // TimeLineResponse의 정적 메소드 사용
                .collect(Collectors.toList());

        return new RecordResponse(
                record.getId(),
                record.getCreatedAt(),
                record.getWorkingout_time(),
                record.getYoutube_url(),
                record.getTotal_score(),
                timelines
        );
    }
}