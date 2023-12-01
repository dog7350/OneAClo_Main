package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.BoardReport;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardReportId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardReportRepository extends JpaRepository<BoardReport, BoardReportId> {

}
