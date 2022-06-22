package com.its.member.service;

import com.its.member.dto.MemberDTO;
import com.its.member.entity.MemberEntity;
import com.its.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.save(memberDTO);
        Long id = memberRepository.save(memberEntity).getId();
        return id;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /**
         * login.html에서 이메일, 비번을 받아오고
         * DB로부터 해당 이메일의 정보를 가져와서
         * 입력받은 비번과 DB에서 조회한 비번의 일치여부를 판단하여
         * 일치하면 로그인 성공, 일치하지 않으면 로그인 실패로 처리
         */
        MemberEntity memberEntity = MemberEntity.save(memberDTO);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEntity.getMemberEmail());
        if(optionalMemberEntity.isEmpty()){
            return null;
        }else{
            if (!optionalMemberEntity.get().getMemberPassword().equals(memberDTO.getMemberPassword())){
                return null;
            }else {
                return MemberDTO.toDTO(optionalMemberEntity.get());
            }
        }
    }

//  선생님 코드
//    public boolean login(MemberDTO memberDTO) {
//        Optional<MemberEntity> optionalMemberEntity =
//                memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
//
//        if(optionalMemberEntity.isPresent()){
//            MemberEntity loginEntity = optionalMemberEntity.get();
//            if(loginEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
//                return true;
//            }else{
//                return false;
//            }
//        }else {
//            return false;
//        }
//    }


    public MemberDTO findById(Long id) {
        Optional<MemberEntity>optionalMemberEntity=memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
//            MemberEntity memberEntity=optionalMemberEntity.get();
//            MemberDTO memberDTO=MemberDTO.toDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity : memberEntityList){
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }



}
