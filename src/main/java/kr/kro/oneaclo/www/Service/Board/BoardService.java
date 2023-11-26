package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;

public interface BoardService {
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    void BoardSave(String BoardUser,String title,String content,String btype);
    BoardDTO BoardInfo(int bno);
    void BoardModify(BoardDTO boardDTO);
    void BoardDel(int bno);
}
