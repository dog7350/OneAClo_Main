package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.BoardFile;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardFileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository<BoardFile, BoardFileId> {

}
