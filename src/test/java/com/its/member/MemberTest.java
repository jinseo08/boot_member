package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(){
        MemberDTO member = new MemberDTO("테스트용 이메일","테스트용 비밀번호","테스트용 이름",99,"테스트용 전화번호");
        return member;
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        Long saveId = memberService.save(newMember());
        MemberDTO memberDTO = memberService.findById(saveId);
        assertThat(newMember().getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("로그인 테스트")
    public void loginTest(){
        String memberEmail = "로그인용이메일";
        String memberPassword = "로그인용비번";
        String memberName = "로그인용이름";
        int memberAge = 99;
        String memberPhone = "로그인용전화번호";
        MemberDTO memberDTO = new MemberDTO(memberEmail, memberPassword, memberName, memberAge, memberPhone);
        Long saveId = memberService.save(memberDTO);
        //로그인 객체 생성 후 로그인
        MemberDTO loginDTO = new MemberDTO();
        loginDTO.setMemberEmail(memberEmail);
        loginDTO.setMemberPassword(memberPassword);
        MemberDTO loginResult = memberService.login(loginDTO);
        //로그인 결과 not null이면 테스트 통과
        assertThat(loginResult).isNotNull();
    }

}
