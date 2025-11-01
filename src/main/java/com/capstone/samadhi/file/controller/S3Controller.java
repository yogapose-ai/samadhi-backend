package com.capstone.samadhi.file.controller;

import com.capstone.samadhi.common.ResponseDto;
import com.capstone.samadhi.file.service.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value="/api/bucket", produces = "application/json; charset=utf-8")
@RequiredArgsConstructor
@Slf4j
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam("file")MultipartFile file) {
        try {
            String fileUrl = s3Service.uploadFile(file);
            return new ResponseEntity<>(new ResponseDto<String>(true, fileUrl), HttpStatus.OK);
        } catch (IOException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(new ResponseDto<String>(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
