package kr.kro.oneaclo.www.Board.Repository;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.*;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.*;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import kr.kro.oneaclo.www.Service.Board.BoardCmtService;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootTest
public class BoardTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    MembersRepository membersRepository;
    @Autowired
    BoardCmtRepository boardCmtRepository;
    @Autowired
    BoardFileRepository boardFileRepository;
    @Autowired
    BoardReportRepository boardReportRepository;
    @Autowired
    BoardCmtReportRepository boardCmtReportRepository;
    @Autowired
    BoardCmtService boardCmtService;
    @Autowired
    BoardService boardService;
    @Autowired
    ModelMapper modelMapper;

    @Test
    public void BoardSave() {
        Optional<Members> result = membersRepository.findById("test");
        Members members = result.orElseThrow();
            for(int i=0; i<99; i++) {
                Board board = Board.builder()
                        .writer(members)
                        .title("제목")
                        .content("내용")
                        .firsttime(null)
                        .lasttime(null)
                        .inquiry(0)
                        .bnogroup(0)
                        .step(0)
                        .indent(0)
                        .build();

                boardRepository.save(board);
            }
    }
    @Test
    public void admin() {
        Optional<Members> result = membersRepository.findById("test2");
        Members members = result.orElseThrow();
        members.admin("a");
        membersRepository.save(members);
    }
    @Test
    public void BoardCmtSave() {
        Optional<Board> result = boardRepository.findByBno(4);
        Board board = result.orElseThrow();

        Optional<Members> result2 = membersRepository.findById("test");
        Members members = result2.orElseThrow();

        BoardCmt boardCmt = BoardCmt.builder()
                .bno(board)
                .writer(members)
                .content("내용")
                .firsttime(null)
                .lasttime(null)
                .cnogroup(3)
                .step(0)
                .indent(0)
                .build();

        boardCmtRepository.save(boardCmt);
    }

    @Test
    public void BoardFileSave() {
        Optional<Board> result = boardRepository.findByBno(3);
        Board board = result.orElseThrow();

        BoardFile boardFile = BoardFile.builder()
                .bno(board)
                .filename("테스트용파일")
                .build();

        boardFileRepository.save(boardFile);
    }

    @Test
    public void BoardReportSave() {
        Optional<Board> result = boardRepository.findByBno(3);
        Board board = result.orElseThrow();

        Optional<Members> result2 = membersRepository.findById("test");
        Members members = result2.orElseThrow();

        BoardReport boardReport = BoardReport.builder()
                .bno(board)
                .reporter(members)
                .build();

        boardReportRepository.save(boardReport);
    }

    @Test
    public void BoardCmtReportSave() {
        Optional<Members> result2 = membersRepository.findById("test");
        Members members = result2.orElseThrow();

        Optional<BoardCmt> result3 = boardCmtRepository.findById(new BoardCmtId(3,1));
        BoardCmt boardCmt = result3.orElseThrow();

        BoardCmtReport boardCmtReport = BoardCmtReport.builder()
                .boardCmt(boardCmt)
                .reporter(members)
                .build();

        boardCmtReportRepository.save(boardCmtReport);
    }
    @Test
    public void testSearch() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("title")
                .Keyword("1")
                .page(1)
                .size(10)
                .build();
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        System.out.println(responseDTO);
    }
    @Test
    public void TestSearch2() {
        int bno = 306;
        List<BoardCmt> boardCmts = boardCmtService.BoardCmtInfo(bno);
        System.out.println(boardCmts);
        for(int i=0;i<boardCmts.size();i++) {
            System.out.println(boardCmts.get(i).getCno());
        }
    }
}
