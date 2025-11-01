package com.capstone.samadhi.record.controller;

import com.capstone.samadhi.common.ResponseDto;
import com.capstone.samadhi.record.dto.RecordRequest;
import com.capstone.samadhi.record.dto.RecordResponse;
import com.capstone.samadhi.record.service.RecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/record")
public class RecordController {
    private final RecordService recordService;

    @PostMapping("/")
    @Operation(summary = "레포트 생성", description = "레포트를 생성할 때 사용하는 API")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", content = {@Content(schema= @Schema(implementation = ResponseDto.class)
            )})
    })
    public ResponseEntity<ResponseDto<RecordResponse>> createMessage(/*@AuthenticationPrincipal UserDetails customUserDetails,*/ @Valid @RequestBody RecordRequest request){
        // Member member = memberService.findMemberByEmail(customUserDetails.getUsername());
        return ResponseEntity.ok(recordService.save(request));
    }

    @GetMapping("/{record_id}")
    @Operation(summary = "특정 레코드 조회", description = "특정 레코드를 조회할 때 사용하는 API")
    @ApiResponses(value={
            @ApiResponse(responseCode="200", content = {@Content(schema= @Schema(implementation = ResponseDto.class)
            )})
    })
    public ResponseEntity<?> getRecordById(/*@AuthenticationPrincipal UserDetails customUserDetails,*/ @PathVariable("record_id") Long id){
        //Member member = memberService.findMemberByEmail(customUserDetails.getUsername());
        return ResponseEntity.ok(recordService.findById(id));
    }
//    @GetMapping("/")
//    @Operation(summary = "내 레포트 목록 조회", description = "내가 생성한 레포트 목록을 페이징하여 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "조회 성공",
//                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
//    })
//    public ResponseEntity<ResponseDto<List<RecordResponse>>> getMyRecords(
//            @AuthenticationPrincipal UserDetails customUserDetails,
//            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
//            Pageable pageable
//    ) {
//        String userEmail = customUserDetails.getUsername();
//        ResponseDto<List<RecordResponse>> responseBody = recordService.findByUser(userEmail, pageable);
//
//        return ResponseEntity.ok(responseBody);
//    }
}
