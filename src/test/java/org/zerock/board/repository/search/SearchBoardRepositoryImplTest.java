package org.zerock.board.repository.search;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.board.repository.BoardRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchBoardRepositoryImplTest {

    @Autowired
    private BoardRepository repository;

    @Test
    void searchPage1() {
        repository.search1();
    }
}