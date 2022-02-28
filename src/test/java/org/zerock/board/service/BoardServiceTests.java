package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.BoardDTO;

@SpringBootTest
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
}
