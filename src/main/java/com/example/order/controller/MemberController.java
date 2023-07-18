package com.example.order.controller;

import com.example.order.config.UserInfo;
import com.example.order.model.member.JoinMemberForm;
import com.example.order.model.member.LoginForm;
import com.example.order.model.member.Member;
import com.example.order.repository.MemberRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("member")
@Controller
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 페이지 이동
     * @param model
     * @return member/joinForm.html
     */
    @GetMapping("join")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new JoinMemberForm());
        return "member/joinForm";
    }

    /**
     * 회원가입
     * @param joinForm
     * @param bindingResult
     * @return
     */
    @PostMapping("join")
    public String join(@Validated @ModelAttribute("joinForm") JoinMemberForm joinForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("bindingResult: {}", bindingResult);
            return "member/joinForm";
        }

        // 동일한 아이디로 가입된 회원정보가 있는지 검색
        Member member = memberService.findMemberById(joinForm.getMember_id());
        if (member != null) {
            bindingResult.rejectValue("member_id","idDuplicateError", "이미 사용중인 아이디 입니다.");
            log.info("bindingResult: {}", bindingResult);
            return "member/joinForm";
        }

        // 회원정보를 저장한다.
        memberService.saveMember(JoinMemberForm.toMember(joinForm));

        return "redirect:/";
    }

    /**
     * 로그인 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("login")
    public String loginForm(@RequestParam(value = "error", required = false) boolean error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model) {
        log.info("error: {}", error);
        log.info("exception: {}", exception);

        if (error) {
            model.addAttribute("error", true);
            model.addAttribute("exception", exception);
        }

        model.addAttribute("loginForm", new LoginForm());
        return "member/loginForm";
    }

    // 시큐리티 로그인 성공
    @GetMapping("login-success")
    public String loginSuccess(@AuthenticationPrincipal UserInfo userInfo, Model model) {
        log.info("로그인 성공, userInfo: {}", userInfo);
        model.addAttribute("loginUser", userInfo);
        return "redirect:/";
    }

    // 시큐리티 로그인 실패
    @GetMapping("login-failed")
    public String loginFailed() {
        log.info("로그인 실패");
        return "/member/loginForm";
    }

    @GetMapping("logout")
    public String logout() {
        log.info("로그아웃");
        return "redirect:/";
    }

}
