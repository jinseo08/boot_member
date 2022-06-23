package com.its.member;

import com.its.member.dto.MemberDTO;
import com.its.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import javax.transaction.Transactional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService memberService;

    public MemberDTO newMember(int i){
        MemberDTO member = new MemberDTO("테스트용이메일"+i, "테스트용비밀번호"+i, "테스트용이름"+i, 99+i, "테스트용전화번호"+i);
        return member;
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원가입 테스트")
    public void memberSaveTest(){
        Long saveId = memberService.save(newMember(1));
        MemberDTO memberDTO = memberService.findById(saveId);
        assertThat(newMember(1).getMemberEmail()).isEqualTo(memberDTO.getMemberEmail());
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


    @Test
    @DisplayName("회원 데이터 저장")
    public void memberSave(){
        IntStream.rangeClosed(1, 20).forEach(i->{
            memberService.save(newMember(i));
        });
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 삭제 테스트")
    public void memberDeleteTest(){
        /**
         * 신규회원 등록
         * 삭제처리
         * 해당 회원으로 조회시 null이면 통과
         * **/
        Long saveId = memberService.save(newMember(999));
        memberService.deleteById(saveId);
        assertThat(memberService.findById(saveId)).isNull();
    }

}
