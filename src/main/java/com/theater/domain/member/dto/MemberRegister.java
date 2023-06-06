package com.theater.domain.member.dto;

import com.theater.domain.member.Member;

public class MemberRegister {

    public static MemberShowDto changeToShow(Member member) {
        return new MemberShowDto(
                member.getId(),
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                member.getMoney()
        );
    }
}
