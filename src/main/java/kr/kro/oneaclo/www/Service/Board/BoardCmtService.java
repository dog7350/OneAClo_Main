package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;

import java.util.List;

public interface BoardCmtService {
    void CmtSave(BoardCmtDTO boardCmtDTO);
    void CmtModify(BoardCmtDTO boardCmtDTO);
    List<BoardCmt> BoardCmtInfo(int bno);
}
