package org.zerock.board.dto;


import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO를 선정하는 기준 : 화면에 전달하는 데이터이거나, 화면쪽에서 전달되는 데이터를 기준으로 합니다.
 * Entity 클래스와 구성이 일치하지 않는 경우가 많다.
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {

    private Long bno;

    private String title;

    private String content;

    private String writerEmail; //작성자의 이메일(id)

    private String writerName;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private int replyCount; //해당 게시글의 댓글 수
}
