package com.example.order.model.member.dto;

import com.example.order.model.member.Member;
import lombok.Getter;

@Getter
public class MemberUpdateRequestDto {
    private String password;
    private String email;

    public Member toEntity(String member_id) {
        return Member.builder()
                .member_id(member_id)
                .password(password)
                .email(email)
                .build();
    }
}
