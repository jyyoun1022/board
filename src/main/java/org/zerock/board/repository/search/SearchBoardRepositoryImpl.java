package org.zerock.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import java.util.List;


@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl(){
        super(Board.class);
    }

    @Override
    public Board search1() {

       log.info("search1....");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;


        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count()).groupBy(board);
        tuple.groupBy(board);

        log.info("====================");
        log.info(tuple);
        log.info("====================");

        List<Tuple> result = tuple.fetch();
        log.info(result);

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage.....");

        //q도메인생성
        QMember member = QMember.member;
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        //쿼리생성 board를 기준으로  테이블 붙이기
        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        //select 테이블을 3개 붙인거에서 reply테이블 디테일 정보 뽑아내기
        //select b,w,count(r) from Board b Left Join b.writer Left join Reply r On r.board=b;
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        //검색조건 시작
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        //기본검색 조건
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        //테이블 3개가 합쳐진 테이블에서 검색
        //type이 null이 아니면?
        if(type != null){
            String[] typeArr = type.split("");

            //검색조건2를 작성하기
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            //title,writer,content
            for (String t : typeArr) {
                switch (t){
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);//bno가 0이상이면서 +type조건
        }
        //tuple=table 3개를 붙인거 +위에서 만든 검색조건
        tuple.where(booleanBuilder);
        //한줄로 만들기 위해 그룹바이
        tuple.groupBy(board);
        //쿼리문 실행
        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;
    }
}
