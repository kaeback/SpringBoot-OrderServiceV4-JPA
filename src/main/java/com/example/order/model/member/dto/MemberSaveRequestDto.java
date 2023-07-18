package com.example.order.model.member.dto;

import com.example.order.model.member.GenderType;
import com.example.order.model.member.Member;
import com.example.order.model.member.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @Schema : 입력 항목에 대한 정보를 작성
 * - description: 항목 설명
 * - defaultValue: 기본값
 * - allowableValues: 허용값(enum)
 */
@Schema(description = "회원가입 요청 DTO 객체")
@Getter
@ToString
@NoArgsConstructor
public class MemberSaveRequestDto {
    @Size(min = 2, max = 20)
    @Schema(description = "아이디", nullable = false , minLength = 4, maxLength = 20)
    private String member_id;
    @Schema(description = "패스워드", nullable = false , minLength = 4, maxLength = 20)
    private String password;
    @Schema(description = "이름", nullable = false , minLength = 2, maxLength = 30)
    private String name;
    @Schema(description = "성별", nullable = false , allowableValues = {"MALE", "FEMALE"})
    private GenderType gender;
    @Schema(description = "생년월일", nullable = false , example = "yyyy-MM-dd")
    private LocalDate birth;
    @Schema(description = "이메일", nullable = true , example = "abc@gmail.com" , maxLength = 100)
    private String email;

    public Member toEntity() {
        return Member.builder()
                .member_id(member_id)
                .password(password)
                .name(name)
                .gender(gender)
                .birth(birth)
                .email(email)
                .role(RoleType.ROLE_USER)
                .provider(null)
                .build();
    }
}
