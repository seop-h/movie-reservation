package com.theater.domain.member.service;

import com.theater.domain.member.Member;
import com.theater.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(Member member) {
        return memberRepository.save(member);
    }

    public Member findMember(String memberId) {
        return memberRepository.findById(memberId);
    }

    public Member modify(String memberId, Member member) {
        return memberRepository.update(memberId, member);
    }

    public Boolean withdraw(Member member) {
        return memberRepository.delete(member);
    }

    public Integer loadMoney(String memberId, Integer money) {
        return memberRepository.updateMoney(memberId, money);
    }

}
