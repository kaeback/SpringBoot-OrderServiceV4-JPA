package com.example.order.config;

import com.example.order.model.member.Member;
import com.example.order.model.member.RoleType;
import com.example.order.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalOAuthUserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();
        // 구글 로그인
        String name = null;
        String email = null;
        switch (provider) {
            case "google":
                name = oAuth2User.getAttribute("name");
                email = oAuth2User.getAttribute("email");
                break;
            case "kakao":
                Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
                name = ((Map<String, String>) kakaoAccount.get("profile")).get("nickname");
                email = (String) kakaoAccount.get("email");
                break;
        }

        Member member = Member.builder()
                .member_id(email)
                .password("1234")
                .name(name)
                .email(email)
                .role(RoleType.ROLE_USER)
                .provider(provider)
                .build();

        // 처음 로그인 하는 아이디이면 회원 가입을 시킨다.
        Optional<Member> findMember = memberRepository.findById(email);
        if (!findMember.isPresent()) {
            memberRepository.save(member);
        }

        return new UserInfo(member, oAuth2User.getAttributes());
    }
}
