package com.its.all.service;

import com.its.all.dto.MemberDTO;
import com.its.all.entity.MemberEntity;
import com.its.all.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Long save(MemberDTO memberDTO) throws IOException {
        MultipartFile memberFile = memberDTO.getMemberFile();
        String member = memberFile.getOriginalFilename();
        member = System.currentTimeMillis() + "_" + member;
        String savePath = "D:\\springboot_img\\" + member;
        if(!memberFile.isEmpty()){
            memberFile.transferTo(new File(savePath));
        }
        memberDTO.setMemberProFile(member);
            MemberEntity memberEntity1 = MemberEntity.toSaveEntity(memberDTO);
            Long id = memberRepository.save(memberEntity1).getId();
            return id;

    }
    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> member = memberRepository.findByMemberMail(memberDTO.getMemberMail());
        if(member.isPresent()){
            MemberEntity login = member.get();
            if(login.getMemberPassword().equals(memberDTO.getMemberPassword())){
                return MemberDTO.toMemberDTO(login);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
    public MemberDTO findById(Long id){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
            return memberDTO;
        }else {
            return null;
        }
    }
    public String check(String memberMail) {
        Optional<MemberEntity> result = memberRepository.findByMemberMail(memberMail);
        if(result.isEmpty()){
            return "ok";
        }else {
            return "no";
        }
    }
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntity = memberRepository.findAll();
        List<MemberDTO> memberList = new ArrayList<>();
        for (MemberEntity member : memberEntity){
//            MemberDTO memberDTO = MemberDTO.test(member);
//            memberList.add(memberDTO); 두줄용 밑에는 한줄
            memberList.add(MemberDTO.toMemberDTO(member));
        }
        return memberList;
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
}
