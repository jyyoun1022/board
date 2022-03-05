package org.zerock.board.services;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;


public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO,Object[]> getList (PageRequestDTO pageRequestDTO);

    //게시물 조회 처리(파라미터로 게물의 번호(bno)를 받아서 처리한다.
    //get()메서드는 EntityToDTO를 리턴하게 합니다.(
    BoardDTO get(Long bno);

    //게시물 삭제 처리
    //게시물을 삭제하려면 FK로 게시물을 참조하고 있는 Reply테이블 역시 삭제해야 한다.
    //잓업의 순서                        1.해당 게시물의 모든 댓글을 삭제
    //                                 2.해당 게시물을 삭제
    //가장 중요한 점은 위의 두작업이 하나의 '트랜잭션'으로 처리되어야 한다는 점!!
    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    //이 메서드는 실제로 게시물을 등록하는 register() 에서 사용합니다.
    default Board dtoToEntity(BoardDTO dto){

        Member member = Member.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(member)
                .build();

        return board;
    }

    //JPQL 에서 나오는 Object[] 타입을 DTO 타입 으로 변환시켜줘야합니다.
    //Object[]의 내용은 Board,Member,그리고 댓글의 수 Long 타입이 나오게 되므로 파라미터는 3개가 됩니다.
    //이기능은 getList() 메서드에 사용합니다.
    default BoardDTO entityToDTO(Board board,Member member,Long replyCount){

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())           //fk 로 member 객체 호출한다.
                .replyCount(replyCount.intValue())      //long 으로 나오므로 int 로 처리하도록
                .build();

        return boardDTO;
    }
}
