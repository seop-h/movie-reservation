package com.theater.domain.member;

import lombok.AllArgsConstructor;
import lombok.Data;

//회원정보
@Data
@AllArgsConstructor
public class Member {

    private String id; //아이디
    private String password; //비밀번호
    private String name; //이름
    private String phone; //전화번호
    private String email; //이메일주소
    private Integer money; //보유금액

}
