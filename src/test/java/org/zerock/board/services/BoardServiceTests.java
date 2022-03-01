package org.zerock.board.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.BoardApplication;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;


@SpringBootTest(classes = BoardApplication.class)
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    void testRegister(){
        BoardDTO dto = BoardDTO.builder()
                .content("Test....Content")
                .title("Test.Title")
                .writerEmail("user55@aaa.com")
                .build();

        Long bno = boardService.register(dto);
    }

    /**
     * dto = BoardDTO(bno=101, title=Test.Title, content=Test....Content, writerEmail=user55@aaa.com, writerName=user 55, regDate=2022-02-28T13:46:11.878653, modDate=2022-02-28T13:46:11.878653, replyCount=0)
     * dto = BoardDTO(bno=100, title=Title...100, content=Content...100, writerEmail=user100@aaa.com, writerName=user 100, regDate=2022-02-27T12:32:21.461664, modDate=2022-02-27T12:32:21.461664, replyCount=5)
     * dto = BoardDTO(bno=99, title=Title...99, content=Content...99, writerEmail=user99@aaa.com, writerName=user 99, regDate=2022-02-27T12:32:21.459577, modDate=2022-02-27T12:32:21.459577, replyCount=3)
     * dto = BoardDTO(bno=98, title=Title...98, content=Content...98, writerEmail=user98@aaa.com, writerName=user 98, regDate=2022-02-27T12:32:21.457170, modDate=2022-02-27T12:32:21.457170, replyCount=8)
     * dto = BoardDTO(bno=97, title=Title...97, content=Content...97, writerEmail=user97@aaa.com, writerName=user 97, regDate=2022-02-27T12:32:21.454646, modDate=2022-02-27T12:32:21.454646, replyCount=3)
     * dto = BoardDTO(bno=96, title=Title...96, content=Content...96, writerEmail=user96@aaa.com, writerName=user 96, regDate=2022-02-27T12:32:21.452412, modDate=2022-02-27T12:32:21.452412, replyCount=2)
     * dto = BoardDTO(bno=95, title=Title...95, content=Content...95, writerEmail=user95@aaa.com, writerName=user 95, regDate=2022-02-27T12:32:21.450315, modDate=2022-02-27T12:32:21.450315, replyCount=4)
     * dto = BoardDTO(bno=94, title=Title...94, content=Content...94, writerEmail=user94@aaa.com, writerName=user 94, regDate=2022-02-27T12:32:21.448238, modDate=2022-02-27T12:32:21.448238, replyCount=4)
     * dto = BoardDTO(bno=93, title=Title...93, content=Content...93, writerEmail=user93@aaa.com, writerName=user 93, regDate=2022-02-27T12:32:21.446131, modDate=2022-02-27T12:32:21.446131, replyCount=3)
     * dto = BoardDTO(bno=92, title=Title...92, content=Content...92, writerEmail=user92@aaa.com, writerName=user 92, regDate=2022-02-27T12:32:21.443672, modDate=2022-02-27T12:32:21.443672, replyCount=3)
     */
    @Test
    public  void testList(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);

        for (BoardDTO dto   : result.getDtoList() ) {
            System.out.println("dto = " + dto);

        }
    }

    /**
     * boardDTO = BoardDTO(bno=100, title=Title...100, content=Content...100,
     * writerEmail=user100@aaa.com, writerName=user 100, regDate=2022-02-27T12:32:21.461664,
     * modDate=2022-02-27T12:32:21.461664, replyCount=5)
     */
    @Test
    void testGet(){

        Long bno = 3L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println("boardDTO = " + boardDTO);
    }

    @Test
    void testRemove(){

        Long bno = 3L;

        boardService.removeWithReplies(bno);

    }
    @Test
    void testModify(){

        BoardDTO result = BoardDTO.builder()
                .bno(4L)
                .title("수정된 제목")
                .content("수정된 내용")
                .build();

        boardService.modify(result);
    }

}

