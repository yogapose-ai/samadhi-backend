package com.capstone.samadhi.record.dto;

import com.capstone.samadhi.record.entity.TimeLine;

import java.time.LocalDateTime;

public record TimeLineResponse(
        int youtube_start_sec,
        int youtube_end_sec,
        String pose,
        int score
) {

    public static TimeLineResponse from(TimeLine timeLine) {
        return new TimeLineResponse(
                timeLine.getYoutube_start_sec(),
                timeLine.getYoutube_end_sec(),
                timeLine.getPose(),
                timeLine.getScore()
        );
    }
}