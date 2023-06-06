package com.theater.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberShowDto {

    private String id; //아이디
    private String name; //이름
    private String phone; //전화번호
    private String email; //이메일주소
    private Integer money; //보유금액

}
