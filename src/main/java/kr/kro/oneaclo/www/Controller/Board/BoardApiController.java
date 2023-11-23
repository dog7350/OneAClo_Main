package kr.kro.oneaclo.www.Controller.Board;

import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @GetMapping("/p/BoardInfo")
    public String BoardInfo(@RequestParam int bno, Model model, HttpSession session) {
        BoardDTO dto = boardService.BoardInfo(bno);
        model.addAttribute("dto",dto);

        String ActiveUser = tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),"id");
        model.addAttribute("ActiveUser",ActiveUser);

        List<BoardCmt> boardCmts = boardService.BoardCmtInfo(bno);
        model.addAttribute("CmtList",boardCmts);
        return "views/Board/BoardInfo";
    }

    @PostMapping("/p/CmtReg")
    public String CmtReg(BoardCmtDTO boardCmtDTO) {
        System.out.println(boardCmtDTO.getBno());
        return "redirect:/p/BoardInfo";
    }
}
