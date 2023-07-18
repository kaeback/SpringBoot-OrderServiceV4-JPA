package com.example.order.config;

import com.example.order.model.member.Member;
import com.example.order.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 로그인 요청이 오면 자동으로 UserDetailsService 의 loadUserByUsername 메소드가 실행
 * 필수로 구현해야 한다.
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findById(username);

        if (member.isPresent()) {
            return new UserInfo(member.get());
        }
        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }
}
