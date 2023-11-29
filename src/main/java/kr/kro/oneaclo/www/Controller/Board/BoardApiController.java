package kr.kro.oneaclo.www.Controller.Board;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Service.Board.BoardCmtService;
import kr.kro.oneaclo.www.Service.Board.BoardFileService;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardApiController {

    private final TokenProcess tokenProcess;
    private final BoardService boardService;
    private final BoardCmtService boardCmtService;
    private final BoardFileService boardFileService;

    private void UserModelInfo(HttpSession session, Model model, String want) {model.addAttribute(want, tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"), want));}
    private String UserString(HttpSession session,String want) {return tokenProcess.getMembersToken((String) session.getAttribute("UserInfo"),want);}
    @PostMapping("/p/BoardSave")
    private String BoardSave(BoardDTO DTO, BoardFileDTO FileDTO, HttpSession session, @RequestParam MultipartFile boardfile) {
        int Bno = boardService.BoardSave(UserString(session,"id"),DTO.getTitle(), DTO.getContent(),DTO.getBtype());
        String Btype = "f";
        if(UserString(session,"auth").equals("a")){
            Btype = DTO.getBtype();
        }
        boardService.BoardGroup(Bno,Btype);
        FileDTO.setBno(Bno);
        if(!Objects.equals(FileDTO.getFilename(), "")) {
            boardFileService.FilaNameSave(FileDTO);
            boardFileService.FileSave(Bno,boardfile);
        }
        return "redirect:/board/list";
    }
    @PostMapping("/p/CmtReg")
    public String CmtReg(BoardCmtDTO boardCmtDTO) {
        boardCmtService.CmtSave(boardCmtDTO);
        return "redirect:/board/p/BoardInfo?bno="+boardCmtDTO.getBno().getBno();
    }
    @PostMapping("/p/CmtModify")
    public String CmtModify(BoardCmtDTO boardCmtDTO) {
        boardCmtService.CmtModify(boardCmtDTO);
        return "redirect:/board/p/BoardInfo?bno="+boardCmtDTO.getBno().getBno();
    }

    @PostMapping("/p/BoardModifySave")
    public String BoardModifySave(BoardDTO boardDTO,BoardFileDTO boardFileDTO,@RequestParam MultipartFile boardfile) {
        boardService.BoardModify(boardDTO);
        BoardFileDTO dto = boardFileService.BoardFileInfo(boardDTO.getBno());

        if(dto != null) {
            if(!dto.getFilename().equals(boardFileDTO.getBno()+boardFileDTO.getFilename())) {
                boardFileService.FileNameModify(boardFileDTO);
                boardFileService.FileModify(boardfile,boardDTO.getBno());
            }
        }else {
            if(!Objects.equals(boardFileDTO.getFilename(), "")) {
                boardFileService.FilaNameSave(boardFileDTO);
                boardFileService.FileSave(boardDTO.getBno(),boardfile);
            }
        }
        return "redirect:/board/p/BoardInfo?bno="+boardDTO.getBno();
    }

    @GetMapping("/p/BoardDel")
    public String BoardDel(@RequestParam int bno) {
        boardService.BoardDel(bno);
        return "redirect:/board/list";
    }

    @GetMapping("/p/BoardCmtDel")
    public String BoardCmtDel(@RequestParam int cno,@RequestParam int bno) {
        boardCmtService.CmtDel(bno,cno);
        return "redirect:/board/p/BoardInfo?bno="+bno;
    }

    @PostMapping("/p/BoardReplySave")
    public String BoardReplySave(BoardDTO DTO,HttpSession session) {
        boardService.BoardReplySave(DTO,UserString(session,"id"));
        return "redirect:/board/list";
    }

    @PostMapping("/p/CmtComment")
    public String CmtComment(BoardCmtDTO boardCmtDTO,HttpSession session) {
        if(boardCmtDTO.getStep() > 0) {
            boardCmtDTO.setContent("@"+boardCmtDTO.getWriter().getId()+"   "+boardCmtDTO.getContent());
        }
        boardCmtService.BoardCmtComment(boardCmtDTO,UserString(session,"id"));
        return "redirect:/board/p/BoardInfo?bno="+boardCmtDTO.getBno().getBno();
    }
}
