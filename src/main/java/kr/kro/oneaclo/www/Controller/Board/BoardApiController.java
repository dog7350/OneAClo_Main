package kr.kro.oneaclo.www.Controller.Board;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardApiController {

    private final TokenProcess tokenProcess;
    private final BoardService boardService;

    @PostMapping("/p/BoardSave")
    private String BoardSave(BoardDTO DTO, BoardFileDTO FileDTO, HttpSession session) {
        String[] BoardUserinfo = {tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),"id"),
                        tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),"auth")};
        boardService.BoardSave(BoardUserinfo,DTO.getTitle(), DTO.getContent());
        return "redirect:/board/list";
    }
}
