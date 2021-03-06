package org.zerock.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;
import org.zerock.board.entity.QMember;
import org.zerock.board.entity.QReply;

import java.util.List;
import java.util.stream.Collectors;


@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("search1.......");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());

        tuple.groupBy(board);
//        jpqlQuery.select(board).where(board.bno.eq(1L));

        log.info("=============================");
        log.info(tuple);
        log.info("=============================");

        List<Tuple> result = tuple.fetch();

        log.info(result);

        return null;


    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        log.info("searchPage....!");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));


        //SELECT b,w FROM Board b LEFT JOIN b.writer w LEFT JOIN REPLY r ON r.board=b;

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                }
            }
            booleanBuilder.or(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class,"board");

            tuple.orderBy(new OrderSpecifier(direction,orderByExpression.get(prop)));
        });

        tuple.groupBy(board);

        tuple.offset(pageable.getOffset());
        tuple.stream().limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info(result);

        long count = tuple.fetchCount();

        log.info("Count: "+count);


        return new PageImpl<Object[]>(
                result.stream().map(t->t.toArray()).collect(Collectors.toList()),pageable,count);

    }
}

//    @Override
//    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
//
//        log.info("searchPage.....");
//
//        //q???????????????
//        QMember member = QMember.member;
//        QBoard board = QBoard.board;
//        QReply reply = QReply.reply;
//
//        //???????????? board??? ????????????  ????????? ?????????
//        JPQLQuery<Board> jpqlQuery = from(board);
//
//        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));
//
//        //select ???????????? 3??? ??????????????? reply????????? ????????? ?????? ????????????
//        //select b,w,count(r) from Board b Left Join b.writer Left join Reply r On r.board=b;
//        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());
//
//        //???????????? ??????
//        BooleanBuilder booleanBuilder = new BooleanBuilder();
//        //???????????? ??????
//        BooleanExpression expression = board.bno.gt(0L);
//
//        booleanBuilder.and(expression);
//
//        //????????? 3?????? ????????? ??????????????? ??????
//        //type??? null??? ??????????
//        if(type != null){
//            String[] typeArr = type.split("");
//
//            //????????????2??? ????????????
//            BooleanBuilder conditionBuilder = new BooleanBuilder();
//            //title,writer,content
//            for (String t : typeArr) {
//                switch (t){
//                    case "t":
//                        conditionBuilder.or(board.title.contains(keyword));
//                        break;
//                    case "w":
//                        conditionBuilder.or(member.email.contains(keyword));
//                        break;
//                    case "c":
//                        conditionBuilder.or(board.content.contains(keyword));
//
//                }
//            }
//            booleanBuilder.and(conditionBuilder);//bno??? 0??????????????? +type??????
//        }
//        //tuple=table 3?????? ????????? +????????? ?????? ????????????
//        tuple.where(booleanBuilder);
//        //????????? ????????? ?????? ????????????
//        tuple.groupBy(board);
//        //????????? ??????
//        List<Tuple> result = tuple.fetch();
//
//        log.info(result);
//
//        return null;
//    }
