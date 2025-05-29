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
    public MemberEntity register(MemberEntity member) {
        // TODO: 이메일 중복 검사, 비밀번호 암호화(예: BCrypt) 적용
        // 필요하다면 member.setCreatedAt(new Date()); 같은 초기화 로직 추가
        return memberRepository.save(member);
    }

    /** 로그인 (이메일/비번 일치 시 MemberEntity 반환, 아니면 null) */
    public MemberEntity login(MemberEntity member) {
        return memberRepository.findById(member.getEmail())
                .filter(e -> e.getPassword().equals(member.getPassword()))
                .orElse(null);
    }
}
