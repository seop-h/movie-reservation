package com.theater.domain.member.repository;

import com.theater.domain.member.Member;
import com.theater.domain.member.dto.MemberUpdateDto;

//회원 저장소 인터페이스
public interface MemberRepository {

    Member save(Member member); //회원 정보 저장(회원가입)

    Member findById(String memberId); //회원 정보 조회(마이페이지)

    Member update(String memberId, MemberUpdateDto memberDto); //회원 정보 수정

    Boolean delete(Member member); //회원 정보 삭제(탈퇴)

    Integer updateMoney(String memberId, Integer money); //금액 충전

}
