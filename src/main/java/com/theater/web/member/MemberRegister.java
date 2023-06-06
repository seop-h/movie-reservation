package com.theater.web.member;

import com.theater.domain.member.Member;

public class MemberRegister {

    static MemberShowDto change(Member member) {
        return new MemberShowDto(
                member.getId(),
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                member.getMoney()
        );
    }

}
