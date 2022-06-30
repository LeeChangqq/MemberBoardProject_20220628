package com.its.all.service;

import com.its.all.common.PagingConst;
import com.its.all.dto.BoardDTO;
import com.its.all.dto.CommentDTO;
import com.its.all.entity.BoardEntity;
import com.its.all.entity.CommentEntity;
import com.its.all.entity.MemberEntity;
import com.its.all.repository.BoardRepository;
import com.its.all.repository.CommentRepository;
import com.its.all.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    public Page<BoardDTO> paging(Pageable pageable) {

        int page = pageable.getPageNumber(); //요청페이지값 가져옴.
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        // 삼항연산자
        page = (page == 1)? 0: (page-1);
        // Page<BoardEntity> => Page<BoardDTO>
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // BoardEntity => BoardDTO 객체 변환
        // board: BoardEntity 객체
        // new Board() 생성자
        Page<BoardDTO> boardList = boardEntities.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime(),
                        board.getBoardContents()
                ));
        return boardList;
    }
    public Page<CommentDTO> paging2(Pageable pageable) {

        int page = pageable.getPageNumber(); //요청페이지값 가져옴.
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        // 삼항연산자
        page = (page == 1)? 0: (page-1);
        // Page<BoardEntity> => Page<BoardDTO>
        Page<CommentEntity> comment = commentRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // BoardEntity => BoardDTO 객체 변환
        // board: BoardEntity 객체
        // new Board() 생성자
        Page<CommentDTO> commentDTOS = comment.map(
                comment1 -> new CommentDTO(comment1.getId(),
                        comment1.getCommentWriter(),
                        comment1.getCommentContents(),
                        comment1.getCreatedTime(),
                        comment1.getBoardEntity().getId()
                ));
        return commentDTOS;
    }

    public Long save(BoardDTO boardDTO) throws IOException {
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFileName = boardFile.getOriginalFilename();
        boardFileName = System.currentTimeMillis() + "_" + boardFileName;
        String savePath = "D:\\springboot_img\\" + boardFileName;
        if(!boardFile.isEmpty()){
            boardFile.transferTo(new File(savePath));
        }

        boardDTO.setBoardFileName(boardFileName);

        // toEntity 메서드에 회원 엔티티를 같이 전달해야 함.
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberMail(boardDTO.getBoardWriter());
        if(optionalMemberEntity.isPresent()){
            MemberEntity memberEntity = optionalMemberEntity.get();
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO, memberEntity);
            Long id = boardRepository.save(boardEntity).getId();
            return id;
        }else {
            return null;
        }

    }
    @Transactional
    public BoardDTO detail(Long id) {
        boardRepository.boardHits(id);
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        // 조회수 처리
        // native sql: update board_table set boardHits=boardHits+1 where id=?
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toMemberDTO(boardEntity);
            return boardDTO;
        }else {
            return null;
        }
    }
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
    public void update(BoardDTO boardDTO) throws IOException{
        MultipartFile boardFile = boardDTO.getBoardFile();
        String boardFileName = boardFile.getOriginalFilename();
        boardFileName = System.currentTimeMillis() + "_" + boardFileName;
        String savePath = "D:\\springboot_img\\" + boardFileName;
        boardFile.transferTo(new File(savePath));
        boardDTO.setBoardFileName(boardFileName);
        boardRepository.save(BoardEntity.toUpdateEntity(boardDTO));
    }
    public BoardDTO detail2(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        // 조회수 처리
        // native sql: update board_table set boardHits=boardHits+1 where id=?
        if(optionalBoardEntity.isPresent()){
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toMemberDTO(boardEntity);
            return boardDTO;
        }else {
            return null;
        }
    }

//    public List<BoardDTO> search(String q,String type) {
//        if(type.equals("boardTitle")) {
//            List<BoardEntity> boardEntityList = boardRepository.findByBoardTitleContaining(q);
//            List<BoardDTO> boardDTOList = new ArrayList<>();
//
//            for(BoardEntity boardEntity: boardEntityList) {
//                boardDTOList.add(BoardDTO.toMemberDTO(boardEntity));
//            }
//            return boardDTOList;
//        }else {
//            List<BoardEntity> boardEntityList = boardRepository.findByBoardWriterContaining(q);
//            List<BoardDTO> boardList = new ArrayList<>();
//
//            for(BoardEntity boardEntity: boardEntityList) {
//                boardList.add(BoardDTO.toMemberDTO(boardEntity));
//            }
//            return boardList;
//        }

    //    }
    public Page<BoardDTO> search(String type, String keyword, Pageable pageable) {
        int page = pageable.getPageNumber();
        page = (page == 1) ? 0 : (page - 1);

        Page<BoardEntity> searchEntity = null;
        boardRepository.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));


        if (type.equals("boardTitle")){
            searchEntity = boardRepository.findByBoardTitleContainingIgnoreCase(keyword,PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            searchEntity = boardRepository.findByBoardWriterContainingIgnoreCase(keyword,PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        }

        Page<BoardDTO> boardList = searchEntity.map(
                board -> new BoardDTO(board.getId(),
                        board.getBoardTitle(),
                        board.getBoardWriter(),
                        board.getBoardHits(),
                        board.getCreatedTime(),
                        board.getBoardContents()
                ));

        return boardList;
    }
}