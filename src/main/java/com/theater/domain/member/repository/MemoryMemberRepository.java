package com.theater.domain.member.repository;

import com.theater.domain.member.Member;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static final Map<String, Member> store = new ConcurrentHashMap<>(); //회원 정보가 실제로 저장되는 장소(key: 회원 id, value: 회원 정보)

    @Override
    public Member save(Member member) {
        store.put(member.getId(), member);
        return store.get(member.getId());
    }

    @Override
    public Member findById(String memberId) {
        return store.get(memberId);
    }

    @Override
    public Member update(String memberId, Member member) {
        Member findMember = store.get(memberId);
        updateMemberInfo(findMember, member); //findMember의 정보를 member로 변경(money는 변경 X)
        return store.get(memberId);
    }

    @Override
    public Boolean delete(Member member) {
        if (store.remove(member.getId()) != null) return true;
        return false;
    }

    @Override
    public Integer updateMoney(String memberId, Integer money) {
        Member findMember = store.get(memberId);
        findMember.setMoney(findMember.getMoney() + money);
        return findMember.getMoney();
    }

    private void updateMemberInfo(Member findMember, Member member) {
        findMember.setPassword(member.getPassword());
        findMember.setName(member.getName());
        findMember.setPhone(member.getPhone());
        findMember.setEmail(member.getEmail());
    }
}
