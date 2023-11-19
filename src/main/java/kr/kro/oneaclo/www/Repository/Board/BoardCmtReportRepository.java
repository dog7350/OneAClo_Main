package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.BoardCmtReport;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtReportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCmtReportRepository extends JpaRepository<BoardCmtReport, BoardCmtReportId> {

}
