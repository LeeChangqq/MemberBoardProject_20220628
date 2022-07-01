package com.its.all.dto;

import com.its.all.entity.BoardEntity;
import com.its.all.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime commentCreatedDate;

    public CommentDTO(Long id, String commentWriter, String commentContents,LocalDateTime commentCreatedDate,Long boardId) {
        this.id= id;
        this.commentWriter = commentWriter;
        this.commentContents = commentContents;
        this.commentCreatedDate = commentCreatedDate;
        this.boardId = boardId;
    }

//    public static CommentDTO toCommentDTO(CommentEntity commentEntity){
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(commentEntity.getId());
//        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
//        commentDTO.setCommentContents(commentEntity.getCommentContents());
//        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
//        commentDTO.setCommentCreatedDate(commentEntity.getCreatedTime());
//        return commentDTO;
//    }


}
