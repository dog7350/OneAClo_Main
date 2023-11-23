package kr.kro.oneaclo.www.Controller.Board;


import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardViewController {

    private final BoardService boardService;
    private final TokenProcess tokenProcess;
    @GetMapping("/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        model.addAttribute("BoardList",responseDTO);
        return "views/Board/BoardList";
    }
    @GetMapping("/p/BoardWrite")
    public String BoardWrite() {
        return "views/Board/BoardWrite";
    }


}
