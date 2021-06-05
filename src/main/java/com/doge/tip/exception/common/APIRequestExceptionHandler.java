package com.doge.tip.exception.common;

import com.doge.tip.exception.user.MissingElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class APIRequestExceptionHandler {

    @ExceptionHandler(value = {APIRequestException.class})
    public ResponseEntity<Object> handleAPIRequestException(RuntimeException e) {
        APIException apiException = new APIException(e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of("Z"))
        );

        return new ResponseEntity<>(apiException, HttpStatus.BAD_REQUEST);
    }

}
