package kr.kro.oneaclo.www.Controller.Board;


import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.DTO.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Page.PageResponseDTO;
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



    private String UserModelInfo(HttpSession session, Model model, String want) {
        String token = (String) session.getAttribute("UserInfo");

        if (token != null) model.addAttribute(want, tokenProcess.getMembersToken(token, want));

        return token;
    }

    @GetMapping("/list")
    public String list(Model model, HttpSession session, PageRequestDTO pageRequestDTO) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        model.addAttribute("BoardList",responseDTO);

        String[] arr ={"id","auth"};
        for(String list:arr) {
            UserModelInfo(session, model, list);
        }

        int step = boardService.StepMax();
        model.addAttribute("StepValue",step);
        return "views/board/BoardList";
    }

    @GetMapping("/p/BoardInfo")
    public String BoardInfo(@RequestParam int bno, Model model, HttpSession session, PageRequestDTO pageRequestDTO) {
        if (session.getAttribute("UserInfo") == null) return "views/mypage/login/LoginForm";

        BoardDTO dto = boardService.BoardInfo(bno);
        model.addAttribute("dto",dto);

        String[] arr ={"id","auth"};
        for (String list:arr) UserModelInfo(session, model, list);

        pageRequestDTO.setSize(100);
        PageResponseDTO<BoardCmtDTO> responseDTO = boardCmtService.BoardCmtList(pageRequestDTO,bno);
        model.addAttribute("CmtList",responseDTO);

        BoardFileDTO boardFileDTO = boardFileService.BoardFileInfo(bno);
        if(boardFileDTO != null) {
            model.addAttribute("FileName",boardFileDTO.getFilename());
        }

        return "views/board/BoardInfo";
    }
    @GetMapping("/p/BoardWrite")
    public String BoardWrite(Model model, HttpSession session) {
        String token = String.valueOf(session.getAttribute("UserInfo"));
        if (token == null) return "views/mypage/login/LoginForm";

        model.addAttribute("Auth",tokenProcess.getMembersToken(token,"auth"));
        return "views/board/BoardWrite";
    }

    @GetMapping("/p/BoardModify")
    public String BoardModify(@RequestParam int bno,Model model, HttpSession session) {
        if (session.getAttribute("UserInfo") == null) return "views/mypage/login/LoginForm";

        BoardDTO boardDTO = boardService.BoardInfo(bno);
        model.addAttribute("dto",boardDTO);
        BoardFileDTO boardFileDTO = boardFileService.BoardFileInfo(bno);
        if(boardFileDTO != null) {
            model.addAttribute("FileDto",boardFileDTO);
        }
        return "views/board/BoardModify";
    }

    @GetMapping("/p/BoardReply")
    public String BoardReply(@RequestParam int bno,Model model, HttpSession session) {
        if (session.getAttribute("UserInfo") == null) return "views/mypage/login/LoginForm";

        BoardDTO boardDTO = boardService.GetBoard(bno);
        model.addAttribute("dto",boardDTO);
        return "views/board/BoardReply";
    }
}
