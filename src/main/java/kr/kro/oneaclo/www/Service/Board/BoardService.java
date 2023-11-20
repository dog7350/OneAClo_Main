package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;

import java.util.List;

public interface BoardService {
    List<Board> BoardGetList();
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
}
