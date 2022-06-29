package com.its.all.dto;

import com.its.all.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    private Long id;
    private String memberMail;
    private String memberPassword;
    private String memberName;
    private String memberEmail;
    private String memberMobile;
    private String memberProFile;
    private MultipartFile memberFile;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberMail(memberEntity.getMemberMail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setMemberProFile(memberEntity.getMemberProfile());
        return memberDTO;
    }
}
