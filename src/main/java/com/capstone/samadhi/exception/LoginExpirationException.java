package com.capstone.samadhi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginExpirationException extends RuntimeException{

    private LoginExpirationException() {super("로그인이 만료되었습니다. 다시 로그인해주세요.");}

    public static final LoginExpirationException EXCEPTION = new LoginExpirationException();
}
