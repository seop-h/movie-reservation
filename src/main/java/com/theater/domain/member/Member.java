package com.theater.domain.member;

import lombok.Data;
import lombok.NoArgsConstructor;

//회원정보
@Data
@NoArgsConstructor
public class Member {

    private String id; //아이디
    private String password; //비밀번호
    private String name; //이름
    private String phone; //전화번호
    private String email; //이메일주소
    private Integer money = 0; //보유금액

    public Member(String id, String password, String name, String phone, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
