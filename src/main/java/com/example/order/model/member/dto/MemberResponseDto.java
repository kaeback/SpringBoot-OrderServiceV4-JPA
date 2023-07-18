package com.example.order.model.member.dto;

import com.example.order.model.member.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private String member_id;
    private String name;
    private String gender;
    private String birth;
    private String email;

    public MemberResponseDto(Member member) {
        this.member_id = member.getMember_id();
        this.name = member.getName();
        if (member.getGender() != null) this.gender = member.getGender().name();
        if (member.getBirth() != null) this.birth = member.getBirth().toString();
        this.email = member.getEmail();
    }
}
