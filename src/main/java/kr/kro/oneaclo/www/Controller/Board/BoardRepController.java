package kr.kro.oneaclo.www.Controller.Board;


import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Service.Board.BoardCmtService;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardRepController {

    private final BoardService boardService;
    private final BoardCmtService boardCmtService;
    private final TokenProcess tokenProcess;
    private String UserString(HttpSession session, String want) {return tokenProcess.getMembersToken(String.valueOf(session.getAttribute("UserInfo")),want);}
    @GetMapping("/p/BoardInqUp")
    public void BoardInqUp(@RequestParam int bno) {
        boardService.InqUp(bno);
    }

    @GetMapping("/p/BoardReport")
    public void BoardReport(@RequestParam int bno, HttpSession session) {
        boardService.BoardReport(UserString(session,"id"),bno);
    }

    @GetMapping("/p/CmtReport")
    public void CmtReport(@RequestParam int bno,@RequestParam int cno,HttpSession session) {
        boardCmtService.CmtReport(bno,cno,UserString(session,"id"));
    }
}
