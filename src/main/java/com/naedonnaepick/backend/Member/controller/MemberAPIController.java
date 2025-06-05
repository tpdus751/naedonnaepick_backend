package com.naedonnaepick.backend.Member.controller;

import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import com.naedonnaepick.backend.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberAPIController {

    private final MemberService memberService;

    /** 회원가입 */
    @PostMapping("/register")
    public ResponseEntity<MemberEntity> register(@RequestBody MemberEntity tryRegistMember) {
        MemberEntity saved = memberService.register(tryRegistMember);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberEntity loginMember, HttpSession session) {
        MemberEntity member = memberService.login(loginMember);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패: 이메일 또는 비밀번호 불일치");
        }

        // ✅ 세션에 로그인 정보 저장
        session.setAttribute("loginUser", member);

        // 필요 정보만 프론트에 전달
        return ResponseEntity.ok(Map.of(
                "first_name", member.getFirst_name(),
                "last_name", member.getLast_name(),
                "nickname", member.getNickname(),
                "email", member.getEmail()
        ));
    }
}
