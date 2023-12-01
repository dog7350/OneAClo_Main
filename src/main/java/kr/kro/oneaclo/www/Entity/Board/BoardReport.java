package kr.kro.oneaclo.www.Entity.Board;

import jakarta.persistence.*;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardReportId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Table(name = "boardreport")
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(BoardReportId.class)
@Builder
public class BoardReport {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bno",columnDefinition = "number(38)",nullable = false)
    private Board bno;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reporter",columnDefinition = "varchar2(50)",nullable = false)
    private Members reporter;
}
