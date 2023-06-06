package com.theater.web.responsedata.extension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.theater.web.responsedata.ResponseResult;
import lombok.Getter;

/**
 * 반환할 데이터(회원, 영화, 상영일정 등)가 없는 경우 이 스펙에 맞춰 응답을 보낸다.
 * TODO 자체적 에러 코드 설정.
 *      HTTP 상태 코드랑 유사하게 가지만 조금 더 세부적으로 나눠서, 에러 코드만 보고도 어느 컨트롤러에서 발생한 어떤 에러인지 알 수 있도록.
 *      ex) 카카오 rest api: code는 별다른 규칙이 없는 음수로 정함
 */
@Getter //getter가 있어야 컨트롤러에서 json 형태로 응답을 보낼 수 있음
public class ErrorResult extends ResponseResult {

    private Integer errorCode; //에러 발생 시 해당 에러에 대응하는 코드

    public ErrorResult(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorResult(String message) {
        super(message);
    }
}
