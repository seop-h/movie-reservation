package com.theater.web.responsedata.extension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.theater.domain.member.Member;
import com.theater.domain.member.dto.MemberShowDto;
import com.theater.web.responsedata.ResponseResult;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //Json 데이터로 반환할 떄 null인 필드는 표시하지 않음
public class MemberResult extends ResponseResult {

    private Member member;
    private MemberShowDto memberShowDto;
    private Integer money;

    public MemberResult(String message, Member member) {
        super(message);
        this.member = member;
    }

    public MemberResult(String message, MemberShowDto memberShowDto) {
        super(message);
        this.memberShowDto = memberShowDto;
    }

    public MemberResult(String message, Integer money) {
        super(message);
        this.money = money;
    }
}
