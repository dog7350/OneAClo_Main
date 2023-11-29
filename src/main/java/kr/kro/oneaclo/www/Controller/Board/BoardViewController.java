package kr.kro.oneaclo.www.Controller.Board;


import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Service.Board.BoardCmtService;
import kr.kro.oneaclo.www.Service.Board.BoardFileService;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardViewController {

    private final BoardService boardService;
    private final TokenProcess tokenProcess;
    private final BoardCmtService boardCmtService;
    private final BoardFileService boardFileService;


    private void UserModelInfo(HttpSession session, Model model, String want) {model.addAttribute(want, tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"), want));}
    private String UserString(HttpSession session,String want) {return tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),want);}
    @GetMapping("/list")
    public String list(Model model, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        model.addAttribute("BoardList",responseDTO);

        int step = boardService.StepMax();
        model.addAttribute("StepValue",step);
        return "views/board/BoardList";
    }

    @GetMapping("/p/BoardInfo")
    public String BoardInfo(@RequestParam int bno, Model model, HttpSession session,PageRequestDTO pageRequestDTO) {
        BoardDTO dto = boardService.BoardInfo(bno);
        model.addAttribute("dto",dto);

        String[] arr ={"id","auth"};
        for(String list:arr) {
            UserModelInfo(session, model, list);
        }
        PageResponseDTO<BoardCmtDTO> responseDTO = boardCmtService.BoardCmtList(pageRequestDTO,bno);
        model.addAttribute("CmtList",responseDTO);
        return "views/Board/BoardInfo";
    }
    @GetMapping("/p/BoardWrite")
    public String BoardWrite(Model model, HttpSession session) {
        String token = (String) session.getAttribute("UserInfo");
        model.addAttribute("Auth",tokenProcess.getMembersToken(token,"auth"));
        return "views/board/BoardWrite";
    }

    @GetMapping("/p/BoardModify")
    public String BoardModify(@RequestParam int bno,Model model) {
        BoardDTO boardDTO = boardService.BoardInfo(bno);
        model.addAttribute("dto",boardDTO);
        BoardFileDTO boardFileDTO = boardFileService.BoardFileInfo(bno);
        if(boardFileDTO != null) {
            model.addAttribute("FileDto",boardFileDTO);
        }
        return "views/board/BoardModify";
    }

    @GetMapping("/p/BoardReply")
    public String BoardReply(@RequestParam int bno,Model model) {
        BoardDTO boardDTO = boardService.GetBoard(bno);
        model.addAttribute("dto",boardDTO);
        return "views/board/BoardReply";
    }
}
