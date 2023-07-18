package com.example.order.controller;

import com.example.order.exception.ExceptionResponse;
import com.example.order.model.member.Member;
import com.example.order.model.member.dto.MemberResponseDto;
import com.example.order.model.member.dto.MemberSaveRequestDto;
import com.example.order.model.member.dto.MemberUpdateRequestDto;
import com.example.order.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("api")
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    // 회원정보 저장
    // @ResponseStatus(HttpStatus.CREATED)

    /**
     * @ApiOperation: API 상세 정보 설정
     * - value: API에 대한 간략한 설명
     * - notes: API에 대한 상세한 설명
     */
    @ApiOperation(value = "회원 정보 저장", notes = "회원 정보를 저장한다.")
    @ApiResponse(responseCode = "200", description = "회원정보 저장 성공", content = @Content(schema = @Schema(implementation = Member.class)))
    @PostMapping("v1/members")
    public ResponseEntity<Member> saveMember(@Validated @RequestBody MemberSaveRequestDto memberSaveRequestDto) {
        log.info("memberSaveRequest: {}", memberSaveRequestDto);
        Member findMember = memberService.findMemberById(memberSaveRequestDto.getMember_id());
        if (findMember != null) {
            throw new RuntimeException("이미 사용중인 아이디 입니다.");
        }
        memberService.saveMember(memberSaveRequestDto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(memberSaveRequestDto.toEntity());
    }

    // 모든 회원정보 조회
    @ApiOperation(value = "모든 회원 정보 조회", notes = "모든 회원 정보를 조회한다.")
    @GetMapping("v1/members")
    public ResponseEntity<List<MemberResponseDto>> findAllMembers() {
        List<Member> members = memberService.findAllMembers();
        List<MemberResponseDto> memberResponseDtos =
                // 스트림을 만든다.
                members.stream()
                // 스트림에서 요소를 꺼내서 다른 요소 변환하거나 정보를 추출한다.
                .map(MemberResponseDto::new)
                // 리스트로 저장한다.
                .collect(Collectors.toList());

        return ResponseEntity.ok(memberResponseDtos);
    }

    // 회원정보 조회

    /**
     * @ApiResponse: 응답 객체에 대한 설정
     * - responseCode: http 응답 상태 코드
     * - description: 응답 객체에 대한 설명
     * - content: Response payload 구조
     *   . schema: payload에서 사용하는 schema
     *     . hidden: schema 숨김 여부
     *     . implementation: schema 대상 클래스
     */
    @ApiOperation(value = "회원 정보 조회", notes = "아이디에 해당하는 회원 정보를 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원정보 조회 성공", content = @Content(schema = @Schema(implementation = MemberResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 회원", content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @GetMapping("v1/members/{id}")
    /**
     * @Parameter: 매개변수의 정보를 설정
     * - name: 매개변수의 이름
     * - description: 매개변수 설명
     * - in: 매개변수 위치(query, header, path, cookie)
     */
    public ResponseEntity<MemberResponseDto> findMember(@Parameter(name = "id", description = "회원의 아이디", in = ParameterIn.PATH) @PathVariable String id) {
        log.info("id: {}", id);
        Member member = memberService.findMemberById(id);
        if (member == null) {
            throw new RuntimeException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.ok(new MemberResponseDto(member));
    }
    // 회원정보 수정
    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보를 수정한다.")
    @PutMapping("v1/members/{id}")
    public ResponseEntity<MemberResponseDto> updateMember(@PathVariable String id,
                                                          @RequestBody MemberUpdateRequestDto memberUpdateRequestDto) {
        log.info("id: {}", id);
        log.info("memberUpdateRequestDto: {}", memberUpdateRequestDto);
        Member member = memberService.findMemberById(id);
        if (member == null) {
            throw new RuntimeException(String.format("ID[%s] not found", id));
        }

        return ResponseEntity.ok(new MemberResponseDto(member));
    }

    // 회원정보 삭제
    @ApiOperation(value = "회원 정보 삭제", notes = "회원 정보를 삭제한다.")
    @DeleteMapping("v1/members/{id}")
    public ResponseEntity<String> removeMember(@PathVariable String id) {
        log.info("id: {}", id);
        Member member = memberService.findMemberById(id);
        if (member == null) {
            throw new RuntimeException(String.format("ID[%s] not found", id));
        }
        memberService.removeMember(id);

        return ResponseEntity.ok().body("ok");
    }

}
