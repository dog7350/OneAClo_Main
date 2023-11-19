package kr.kro.oneaclo.www.Entity.Board;

import jakarta.persistence.*;

import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtReportId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;


@Table(name = "bcmtreport")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Builder
@Setter
@IdClass(BoardCmtReportId.class)
public class BoardCmtReport {

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "bno",columnDefinition = "number(38)",referencedColumnName = "bno",nullable = false),
            @JoinColumn(name = "cno",columnDefinition = "number(38)",referencedColumnName = "cno",nullable = false)
    })
    private BoardCmt boardCmt;

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "reporter",columnDefinition = "varchar2(50)",nullable = false)
    private Members reporter;
}
