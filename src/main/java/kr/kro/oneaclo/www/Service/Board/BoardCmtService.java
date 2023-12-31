package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;

public interface BoardCmtService {
    void CmtSave(BoardCmtDTO boardCmtDTO);
    void CmtModify(BoardCmtDTO boardCmtDTO);
    PageResponseDTO<BoardCmtDTO> BoardCmtList(PageRequestDTO pageRequestDTO,int bno);
    void CmtDel(int bno,int cno);
    void BoardCmtComment(BoardCmtDTO boardCmtDTO,String id);
    void CmtReport(int bno, int cno, String id);
}
