package com.naedonnaepick.backend.Member.controller;

import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import com.naedonnaepick.backend.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class MemberAPIController {
    private final MemberService memberService;

    /** 회원가입 */
    @PostMapping("/register")
    public ResponseEntity<MemberEntity> register(@RequestBody MemberEntity tryRegistMember) {
        // Service에서 회원 저장 및 preferences 저장까지 처리
        MemberEntity saved = memberService.register(tryRegistMember);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(saved);
    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<MemberEntity> login(@RequestBody MemberEntity loginMember) {
        MemberEntity member = memberService.login(loginMember);
        if (member == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        return ResponseEntity.ok(member);
    }
}
