package kr.kro.oneaclo.www.Controller.Board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardViewController {

    @GetMapping("/list")
    public String list() {
        return "views/Board/list/BoardList";
    }
}
