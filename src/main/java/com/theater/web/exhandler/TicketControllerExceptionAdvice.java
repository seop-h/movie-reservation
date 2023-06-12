package com.theater.web.exhandler;

import com.theater.web.responsedata.ResponseResult;
import com.theater.web.responsedata.extension.ErrorResult;
import com.theater.web.theater.TheaterController;
import com.theater.web.ticket.TicketController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = {TicketController.class}) //TicketController에서 예외가 발생하면 모두 이곳으로 옴
@Slf4j
public class TicketControllerExceptionAdvice {

    @ExceptionHandler
    public ResponseEntity<ResponseResult> ticketExHandler(Exception e) {
        log.error("[ticketExHandler] ex = {}", e);
        HttpStatus status;
        Integer errorCode = -599;

        if (e instanceof IllegalAccessException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = -500;
        } else if (e instanceof IllegalArgumentException) {
            status = HttpStatus.BAD_REQUEST;
            errorCode = -511;
        } else { //위의 조건문에서 처리하지 못한, 알 수 없는 에러가 발생한 경우 Http 상태코드=500, errorCode=-599 로 반환
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        log.info("[ticketExHandler] status={}, errorCode={}", status, errorCode);
        ErrorResult errorResult = new ErrorResult(e.getMessage(), errorCode);
        return new ResponseEntity(errorResult, status);
    }

}
