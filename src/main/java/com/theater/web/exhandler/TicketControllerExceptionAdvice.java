package com.theater.web.exhandler;

import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.theater.TheaterController;
import com.theater.web.ticket.TicketController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.management.AttributeNotFoundException;
import javax.security.sasl.AuthenticationException;

@RestControllerAdvice(assignableTypes = {TicketController.class}) //TicketController에서 예외가 발생하면 모두 이곳으로 옴
@Slf4j
public class TicketControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ResponseResult> ticketExHandler(Exception e) {
        log.error("[ticketExHandler] ex = {}", e);
        HttpStatus status;
        Integer errorCode = -599;
        String message;

        if (e instanceof AuthenticationException) {
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
            errorCode = -500;
        } else if (e instanceof AttributeNotFoundException) {
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
            errorCode = -501;
        } else if (e instanceof IllegalArgumentException) {
            message = e.getMessage();
            status = HttpStatus.BAD_REQUEST;
            errorCode = -513;
        } else { //위의 조건문에서 처리하지 못한, 알 수 없는 에러가 발생한 경우 Http 상태코드=500, errorCode=-599 로 반환
            message = e.getMessage();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.info("[ticketExHandler] status={}, errorCode={}", status, errorCode);
        ErrorResult errorResult = new ErrorResult(message, errorCode);
        return new ResponseEntity(errorResult, status);
    }

}
