package kr.kro.oneaclo.www.Entity.Board;

import jakarta.persistence.*;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.engine.internal.Cascade;

import java.time.LocalDateTime;


@Table(name = "board")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@SequenceGenerator(name = "BoardSeq",sequenceName = "board_seq",initialValue = 1,allocationSize = 1)
@Builder
public class Board {

    @Id
    @Column(name = "bno",nullable = false,columnDefinition = "number(38)",unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BoardSeq")
    private int bno;

    @ManyToOne
    @JoinColumn(name = "writer",columnDefinition = "varchar(50)")
    private Members writer;

    @Column(name = "title",nullable = false,columnDefinition = "varchar(500)")
    private String title;

    @Column(name = "content",nullable = false,columnDefinition = "clob")
    @Lob
    private String content;

    @Column(name = "firsttime",columnDefinition = "timestamp(6)")
    @ColumnDefault("sysdate")
    private LocalDateTime firsttime;

    @Column(name = "lasttime",columnDefinition = "timestamp(6)")
    @ColumnDefault("sysdate")
    private LocalDateTime lasttime;

    @Column(name = "inquiry",columnDefinition = "number(38)")
    @ColumnDefault("0")
    private int inquiry;

    @Column(name = "btype",columnDefinition = "varchar2(50)")
    @ColumnDefault("'n'")
    private String btype;

    @Column(name = "bnogroup",columnDefinition = "number(4)")
    @ColumnDefault("0")
    private int bnogroup;

    @Column(name = "step",columnDefinition = "number(4)")
    @ColumnDefault("0")
    private int step;

    @Column(name = "indent",columnDefinition = "number(4)")
    @ColumnDefault("0")
    private int indent;
}
