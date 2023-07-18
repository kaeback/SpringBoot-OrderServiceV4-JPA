package com.example.order.model.member;

import lombok.*;

import javax.management.relation.Role;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter @Setter @ToString
public class Member {
    @Id
    private String member_id;
    private String password;
    private String name;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private LocalDate birth;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleType role;
    private String provider;

    @Builder
    public Member(String member_id, String password, String name, GenderType gender, LocalDate birth, String email, RoleType role, String provider) {
        this.member_id = member_id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.role = role;
        this.provider = provider;
    }
}
