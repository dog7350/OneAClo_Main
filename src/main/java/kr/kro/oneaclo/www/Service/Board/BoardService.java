package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;

import java.util.List;

public interface BoardService {
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);

    List<Board> MainNewNormalList();
    List<Board> MainNotificationList();

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
