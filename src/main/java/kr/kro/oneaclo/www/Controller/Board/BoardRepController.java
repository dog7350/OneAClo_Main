package kr.kro.oneaclo.www.Controller.Board;


import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.Repository.Board.BoardReportRepository;
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
    private final TokenProcess tokenProcess;
    private final BoardReportRepository boardReportRepository;
    private String UserString(HttpSession session, String want) {return tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),want);}
    @GetMapping("/p/BoardInqUp")
    public void BoardInqUp(@RequestParam int bno) {
        boardService.InqUp(bno);
    }

    @GetMapping("/p/BoardReport")
    public void BoardReport(@RequestParam int bno, HttpSession session) {
        boardService.BoardReport(UserString(session,"id"),bno);
    }
}
