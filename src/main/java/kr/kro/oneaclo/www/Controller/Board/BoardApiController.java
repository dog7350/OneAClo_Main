package kr.kro.oneaclo.www.Controller.Board;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.kro.oneaclo.www.Common.TokenProcess;
import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.Service.Board.BoardCmtService;
import kr.kro.oneaclo.www.Service.Board.BoardFileService;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    //생성
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
            boardFileService.FileTotalSave(FileDTO,boardfile);
        }
        return "redirect:/board/list";
    }

    @PostMapping("/p/BoardReplySave")
    public String BoardReplySave(BoardDTO DTO,HttpSession session) {
        boardService.BoardReplySave(DTO,UserString(session,"id"));
        return "redirect:/board/list";
    }

    @PostMapping("/p/CmtReg")
    public String CmtReg(BoardCmtDTO boardCmtDTO) {
        boardCmtService.CmtSave(boardCmtDTO);
        return "redirect:/board/p/BoardInfo?bno="+boardCmtDTO.getBno().getBno();
    }
    @PostMapping("/p/CmtComment")
    public String CmtComment(BoardCmtDTO boardCmtDTO,HttpSession session) {
        if(boardCmtDTO.getStep() > 0) {
            boardCmtDTO.setContent("@"+boardCmtDTO.getWriter().getId()+"   "+boardCmtDTO.getContent());
        }
        boardCmtService.BoardCmtComment(boardCmtDTO,UserString(session,"id"));
        return "redirect:/board/p/BoardInfo?bno="+boardCmtDTO.getBno().getBno();
    }

    @GetMapping("/p/download")
    public void download(@RequestParam String name, HttpServletResponse res) throws Exception {
        res.addHeader("Content-disposition","attachment; fileName="+name);
        File file = new File("C:\\UserProfile\\"+name);
        FileInputStream in = new FileInputStream(file);
        FileCopyUtils.copy(in,res.getOutputStream());
        in.close();
    }
    
    //수정
    @PostMapping("/p/CmtModify")
    public String CmtModify(BoardCmtDTO boardCmtDTO) {
        boardCmtService.CmtModify(boardCmtDTO);
        return "redirect:/board/p/BoardInfo?bno="+boardCmtDTO.getBno().getBno();
    }

    private String FileNameCoding(int bno,String Origin) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyHHddHHmmss"));
        String fileName = "Board_" + bno + "_" + today + "_" + Origin;
//        Path savePath = Paths,get(path+"/"+fileName);
        return fileName;
    }
    @PostMapping("/p/BoardModifySave")
    public String BoardModifySave(BoardDTO boardDTO,BoardFileDTO boardFileDTO,@RequestParam MultipartFile boardfile) {
        boardService.BoardModify(boardDTO);
        BoardFileDTO dto = boardFileService.BoardFileInfo(boardDTO.getBno());
        if(dto != null) {
            if(!dto.getFilename().equals(FileNameCoding(boardDTO.getBno(),boardFileDTO.getFilename()))) {
                boardFileService.FileTotalModify(dto,boardFileDTO,boardfile);
            }
        }else {
            if(!Objects.equals(boardFileDTO.getFilename(), "")) {
                boardFileService.FileTotalSave(boardFileDTO,boardfile);
            }
        }
        return "redirect:/board/p/BoardInfo?bno="+boardDTO.getBno();
    }
    
    //삭제 관련
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
}
