package com.its.all.entity;

import com.its.all.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, length = 30, nullable = false)
    private String memberMail;

    @Column(length = 30, nullable = false)
    private String memberPassword;

    @Column(length = 10, nullable = false)
    private String memberName;

    @Column(length = 30, nullable = false)
    private String memberEmail;

    @Column(length = 20, nullable = false)
    private String memberMobile;

    @Column(length = 50, nullable = false)
    private String memberProfile;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, orphanRemoval = false, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberMail(memberDTO.getMemberMail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberProfile(memberDTO.getMemberProFile());
        return memberEntity;
    }
}
