package org.zerock.board.entity;


import lombok.*;
import org.springframework.data.domain.PageRequest;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
//@ToString은 클래스의 모든 멤버변수를 출력한다. exclude 설정을 하지않는다면 member객체 역시 출력됩니다.
//exclude를 해당속성값으로 지정된 변수는 @ToString에서 제외하기 때문에 지연로딩을 할때는 반드시 지정해주는 것이 좋다.
//순환참조를 막는다.!!!(중요!!)
@ToString(exclude = "writer")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    private String title;

    private String content;





    //      FK쪽의 엔티티를 가져올때 PK쪽의 엔티티도 같이 가져온다. (=실제로 Left outer join 처리가 된다)
    //      jpa에서는 관계고민시 fk쪽을 먼저 해석해 본다.
    //      FK쪽을 먼저 해석해보면 N:1관계가 되므로 이는 곧 @ManytoOne를 의미한다.
    //      @ManytoOne는 데이터베이스상에서 외래키의 관계로 연결된 엔티티클래스에 설정한다. Board 클래스는 작성자가 Member 엔티티를 의미한다.
    //      fetch= 연관관계 데이터를 어떻게 가져올지 묻는것. Eager=연관관계가 있는 모든 엔티티, lazy=지연
    @ManyToOne(fetch = FetchType.LAZY)  //명시적으로 Lazy 로딩 지정
    private Member writer;
}
