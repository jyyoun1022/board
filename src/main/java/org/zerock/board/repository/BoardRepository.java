package org.zerock.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long> {

    //한개의 로우(Object) 내에 Object[]로 나온다.
    //내부에 있는 엔티티를 이요할 떄는 ON을 이용하는 부분이 없다.
    //Join,Group By 등을 사용할 때, 적당한 엔티티타입이 존재하지 않는경우가 많기 때문에 Object[]타입을 리턴타입으로 지정
    @Query("select b,w from Board b left join b.writer w where b.bno=:bno")
    Object getBoardWithWriter(@Param("bno")Long bno);

    @Query("Select b,r From Board b Left Join Reply r on r.board = b where b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno")Long bno);

    //하나의 게시물 당 하나의 라인이 될 수 있도록 GROUP BY를 사용.
    //목록(list)와 pageable(페이징,정렬)은 세트라고 생각하자.
    @Query(value = "Select b,w,count(r) from Board b left join b.writer w left join Reply r on r.board=b group by b"
            ,countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable);

    @Query(value="select b,w,count(r) from Board b left join b.writer w left join Reply r on r.board=b where b.bno= :bno")
    Object getBoardByBno(@Param("bno")Long bno);

}
