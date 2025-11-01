package com.capstone.samadhi.exception;

import com.capstone.samadhi.common.ResponseDto;
import com.capstone.samadhi.exception.LoginExpirationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginExpirationException.class)
    public ResponseEntity<?> handlerTokenExpiration(LoginExpirationException ex) {
        return new ResponseEntity<>(new ResponseDto<String>(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<?> handlerInvalidRequestException(InvalidRequestException ex) {
        return new ResponseEntity<>(new ResponseDto<String>(false, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
