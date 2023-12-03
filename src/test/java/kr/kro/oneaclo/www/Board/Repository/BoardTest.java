package kr.kro.oneaclo.www.Board.Repository;

import kr.kro.oneaclo.www.Controller.Mypage.MemberApiController;
import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.Entity.Board.*;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.*;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import kr.kro.oneaclo.www.Service.Board.BoardCmtService;
import kr.kro.oneaclo.www.Service.Board.BoardService;
import kr.kro.oneaclo.www.Service.Mypage.MemberInfoService;
import kr.kro.oneaclo.www.Service.Mypage.MembersService;
import net.bytebuddy.description.type.TypeList;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.util.Optional;
import java.util.Random;

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
    @Autowired
    MemberApiController memberApiController;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    MembersService membersService;
    @Autowired
    MemberInfoService memberInfoService;

    @Test
    public void MemberJoin() throws Exception {
        String[] gender = {"male", "female"};
        String[] area = {"서울 123", "전남 123", "전북 123", "경기 123", "강원 123", "경남 123", "경북 123", "제주 123", "인천 123", "충남 123", "충북 123","부산 123"};
        String[] add = {"아파트","주택","빌라"};
        Random random = new Random();

        for(int i=0;i<600;i++) {
            MemberDTO memberDto = new MemberDTO();
            memberDto.setId("테스터다"+i);
            memberDto.setPw("1234");
            memberDto.setNick("닉네임"+i);
            memberDto.setName("테스트"+i);
            memberDto.setAge(random.nextInt(100)+10);
            memberDto.setGender(gender[random.nextInt(2)]);
            memberDto.setEmail("TEST"+i+"@naver.com");
            memberDto.setZipcode("123123");
            memberDto.setAddress(area[random.nextInt(12)]);
            memberDto.setDetailaddr(add[random.nextInt(3)]);
            memberDto.setPhone("010-00"+i+"-0000");
            Members members = Members.builder()
                .id(memberDto.getId())
                .pw(bCryptPasswordEncoder.encode(memberDto.getPw()))
                .nick(memberDto.getNick())
                .profile("D_Profile.jpg")
                .build();
            membersRepository.save(members);
            memberInfoService.MemberInfoJoin(memberDto);
        }
    }

    @Test
    public void BoardSave() {

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
                .cnogroup(0)
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

        Optional<BoardCmt> result3 = boardCmtRepository.findById(new BoardCmtId(3, 1));
        BoardCmt boardCmt = result3.orElseThrow();

        BoardCmtReport boardCmtReport = BoardCmtReport.builder()
                .boardCmt(boardCmt)
                .reporter(members)
                .build();

        boardCmtReportRepository.save(boardCmtReport);
    }

    @Test
    public void queryTest() {

    }


}
