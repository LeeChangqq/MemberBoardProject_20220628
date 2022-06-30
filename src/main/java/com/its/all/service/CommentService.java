package com.its.all.service;

import com.its.all.dto.CommentDTO;
import com.its.all.entity.BoardEntity;
import com.its.all.entity.CommentEntity;
import com.its.all.entity.MemberEntity;
import com.its.all.repository.BoardRepository;
import com.its.all.repository.CommentRepository;
import com.its.all.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO, Long id, Long id3) {
        Optional<MemberEntity> memberEntity = memberRepository.findById(id);
        MemberEntity member = memberEntity.get();
        Optional<BoardEntity> boardEntity = boardRepository.findById(id3);
        BoardEntity board = boardEntity.get();
        System.out.println(board);
        System.out.println(member);
        System.out.println(commentDTO);
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO,member, board);
        System.out.println(commentEntity);
        Long id2 = commentRepository.save(commentEntity).getBoardEntity().getId();
        return id2;
    }


}
