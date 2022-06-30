package com.its.all.dto;

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



}
