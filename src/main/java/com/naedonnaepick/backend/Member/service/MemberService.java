// src/main/java/com/example/team_project_2_1/Member/service/MemberService.java
package com.naedonnaepick.backend.Member.service;

import com.naedonnaepick.backend.Member.db.MemberRepository;
import com.naedonnaepick.backend.Member.db.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /** 회원가입 */
    public MemberEntity register(MemberEntity m) {
        // TODO: 중복(email) 체크, 비밀번호 암호화 적용
        return memberRepository.save(m);
    }

    /** 로그인(이메일/비번 일치 시 Member 반환, 아니면 null) */
    public MemberEntity login(String email, String rawPassword) {
        return memberRepository.findById(email)
                .filter(m -> m.getPassword().equals(rawPassword))
                .orElse(null);
    }
}
