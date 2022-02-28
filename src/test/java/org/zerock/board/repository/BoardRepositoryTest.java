package org.zerock.board.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void insertBoard() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    /**
     * 1. 엔티티 클래스들이 데이터베이스상에서는 두개 혹은 두개 이상의 테이블로 생성되기때문에 연관관계를 맺고 있다는 것은 데이터베이스 입장으로 보면 join이 필요하다.
     * 2. 실제로 @ManyToOne의 경우 FK쪽의 엔티티를 가져올때 PK쪽의 엔티티도 같이 가져온다. (=실제로 Left outer join 처리가 된다)
     */
    @Test
    @Transactional
    void readTest1() {
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    @Test
    void testReadWithWriter() {

        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arrResult1 = (Object[]) result;
        System.out.println("========================");
        //자바에서 배열 내용을 출력해보려고 배열 자체의 내용의 toString을 하면 배열의 내용이 아니라 배열의 주소값이 나온다.
        //그래서 배열의 내용을 출력하려면 Arrays.toString()을 사용해야 한다!
        System.out.println(Arrays.toString(arrResult1));
    }

    @Test
    void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arrResult2 : result) {
            System.out.println(Arrays.toString(arrResult2));
        }
    }

    @Test
    void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);

        result.get().forEach(row->{
            Object[] arr = (Object[])row;
            System.out.println(Arrays.toString(arr));
        });
    }
}