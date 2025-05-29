// src/main/java/com/example/team_project_2_1/Member/controller/MemberAPIController.java
package com.naedonnaepick.backend.Member.controller;

import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import com.naedonnaepick.backend.Member.dto.MemberLoginRequest;
import com.naedonnaepick.backend.Member.dto.MemberRegisterRequest;
import com.naedonnaepick.backend.Member.dto.MemberResponse;
import com.naedonnaepick.backend.Member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberAPIController {
    private final MemberService memberService;

    /** 회원가입 */
    @PostMapping("/register")
    public ResponseEntity<MemberResponse> register(@RequestBody MemberRegisterRequest req) {
        MemberEntity saved = memberService.register(req.toEntity());
        return ResponseEntity.ok(MemberResponse.from(saved));
    }

    /** 로그인 */
    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@RequestBody MemberLoginRequest req) {
        MemberEntity member = memberService.login(req.getEmail(), req.getPassword());
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(MemberResponse.from(member));
    }
}