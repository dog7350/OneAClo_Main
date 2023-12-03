package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;

public interface BoardService {
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    int BoardSave(String BoardUser,String title,String content,String btype);
    BoardDTO BoardInfo(int bno);
    void BoardModify(BoardDTO boardDTO);
    void BoardDel(int bno);
    void InqUp(int bno);
    void BoardReport(String id,int bno);
    void BoardReplySave(BoardDTO boardDTO,String id);
    BoardDTO GetBoard(int bno);
    void BoardGroup(int bno,String btype);
    int StepMax();

}
