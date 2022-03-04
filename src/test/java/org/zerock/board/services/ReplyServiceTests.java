package org.zerock.board.services;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.dto.ReplyDTO;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    void testGetList(){

        Long bno=97L;

        List<ReplyDTO> list = replyService.getList(bno);

        list.forEach(replyDTO -> System.out.println(replyDTO));
    }

}
