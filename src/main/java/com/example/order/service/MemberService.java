package com.example.order.service;

import com.example.order.model.member.Member;
import com.example.order.model.member.RoleType;
import com.example.order.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    // 회원가입
    @Transactional
    public void saveMember(Member member) {
        // 패스워드 암호화
        String rawPassword = member.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.setPassword(encPassword);
        // 기본 ROLE 부여
        member.setRole(RoleType.ROLE_USER);
        memberRepository.save(member);
    }

    // 회원정보 검색
    public Member findMemberById(String member_id) {
        return memberRepository.findById(member_id).orElse(null);
    }

    // 모든 회원정보 검색
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    // 회원정보 삭제
    public void removeMember(String member_id) {
        memberRepository.deleteById(member_id);
    }
}
