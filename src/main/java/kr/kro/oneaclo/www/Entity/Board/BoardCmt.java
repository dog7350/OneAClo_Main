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
@SequenceGenerator(name = "BoardCmtSeq",sequenceName = "boardcmt_seq",initialValue = 1,allocationSize = 1)
public class BoardCmt {

    @Id
    @ManyToOne
    @JoinColumn(name = "bno", columnDefinition = "number(38)",nullable = false)
    private Board bno;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "BoardCmtSeq")
    @Column(name = "cno",columnDefinition = "number(38)",nullable = false)
    private int cno;

    @ManyToOne
    @JoinColumn(name = "writer", columnDefinition = "varchar(50)")
    private Members writer;

    @Column(name = "content", columnDefinition = "clob", nullable = false)
    @Lob
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

    public void change(String content,LocalDateTime lasttime) {
        this.content = content;
        this.lasttime = lasttime;
    }
}
