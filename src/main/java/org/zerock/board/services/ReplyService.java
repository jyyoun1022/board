package org.zerock.board.services;

import org.zerock.board.dto.ReplyDTO;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    //댓글을 등록하는 기능(register)
    Long register(ReplyDTO replyDTO);
    //특정 게시물의 댓글 리스트를 가져오는 기능(getList)
    List<ReplyDTO> getList(Long bno);
    //댓글을 수정(modify)하고 삭제(remove)하는 기능
    void modify(ReplyDTO replyDTO);

    void remove(Long rno);
    //Reply를 ReplyDTO를 변환하는 기능 entityToDTO
    default ReplyDTO entityToDTO(Reply reply){
        ReplyDTO dto = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build();
        return dto;
    }
    //ReplyDTO를 Reply엔티티로 변환하는 기능 dtoToEntity
    default Reply dtoToEntity(ReplyDTO replyDTO){

        Board board =Board.builder().bno(replyDTO.getBno()).build();

        Reply entity = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return entity;
    }

}
