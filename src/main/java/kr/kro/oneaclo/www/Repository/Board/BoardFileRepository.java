package kr.kro.oneaclo.www.Repository.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardFile;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardFileId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardFileRepository extends JpaRepository<BoardFile, BoardFileId> {
    Optional<BoardFile> findByBno(Board board);
}
