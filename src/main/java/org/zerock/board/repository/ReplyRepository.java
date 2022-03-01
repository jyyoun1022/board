package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    //JPQL 을 이용할때는 수정,삭제를 사용하기 위해서는 @Modifying 어노테이션을 추가해야합니다.
    @Modifying
    @Query("delete from Reply r where r.board.bno= :bno")
    void deleteByBno(@Param("bno")Long bno);
    }



