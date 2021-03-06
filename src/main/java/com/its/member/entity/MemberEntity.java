package com.its.member.entity;

import com.its.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name="member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // id는 @Column 생략가능
    private Long id;

    @Column(name = "memberEmail",length = 50,unique = true)
    private String memberEmail;

    @Column(name = "memberPassword",length = 20)
    private String memberPassword;

    @Column(name = "memberName",length = 20)
    private String memberName;

    @Column(name = "memberAge")
    private int memberAge;

    @Column(name = "memberPhone",length = 30)
    private String memberPhone;


    public static MemberEntity save(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberAge(memberDTO.getMemberAge());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        return memberEntity;
    }

    public static MemberEntity login(MemberDTO memberDTO) {
        MemberEntity loginEntity = new MemberEntity();
        loginEntity.setMemberEmail(memberDTO.getMemberEmail());
        loginEntity.setMemberPassword(memberDTO.getMemberPassword());
        return loginEntity;
    }
}
