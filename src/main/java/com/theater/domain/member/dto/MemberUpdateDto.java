package com.theater.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MemberUpdateDto {

    private String name; //이름
    private String phone; //전화번호
    private String email; //이메일주소

}
