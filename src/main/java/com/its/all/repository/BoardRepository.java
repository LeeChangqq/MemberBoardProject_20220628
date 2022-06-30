package com.its.all.repository;

import com.its.all.entity.BoardEntity;
import com.its.all.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id = :id")
    void boardHits(@Param("id") Long id);
    Page<BoardEntity> findByBoardTitleContainingIgnoreCase(String keyword, Pageable paging);
    Page<BoardEntity> findByBoardWriterContainingIgnoreCase(String keyword,Pageable paging);
    Optional<BoardEntity> findByBoardWriter(String memberMail);

}