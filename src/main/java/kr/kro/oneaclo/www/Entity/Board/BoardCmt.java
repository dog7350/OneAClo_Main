package kr.kro.oneaclo.www.Entity.Board;

import jakarta.persistence.*;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Table(name = "boardcmt")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Builder
@IdClass(BoardCmtId.class)
public class BoardCmt {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bno", columnDefinition = "number(38)",nullable = false,unique = true)
    private Board bno;

    @Id
    @Column(name = "cno",columnDefinition = "number(38)",nullable = false,unique = true)
    private int cno;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "writer", columnDefinition = "varchar(50)")
    private Members writer;

    @Column(name = "content", columnDefinition = "clob", nullable = false)
    private String content;

    @Column(name = "firsttime", columnDefinition = "timestamp(6)")
    @ColumnDefault("sysdate")
    private LocalDateTime firsttime;

    @Column(name = "lasttime", columnDefinition = "timestamp(6)")
    @ColumnDefault("sysdate")
    private LocalDateTime lasttime;

    @Column(name = "cnogroup", columnDefinition = "number(4)")
    private int cnogroup;

    @Column(name = "step", columnDefinition = "number(4)")
    private int step;

    @Column(name = "indent", columnDefinition = "number(4)")
    private int indent;
}
