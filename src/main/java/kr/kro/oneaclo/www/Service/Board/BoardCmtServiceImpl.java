package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.BoardCmtRepository;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCmtServiceImpl implements BoardCmtService{

    private final BoardCmtRepository boardCmtRepository;
    private final MembersRepository membersRepository;
    private final ModelMapper modelMapper;

    //생성
    public void CmtSave(BoardCmtDTO boardCmtDTO) {
        BoardCmt boardCmt = BoardCmt.builder()
                .content(boardCmtDTO.getContent())
                .firsttime(null)
                .lasttime(null)
                .writer(boardCmtDTO.getWriter())
                .bno(boardCmtDTO.getBno())
                .build();

        boardCmtRepository.save(boardCmt);
        boardCmt.CnoGroup(boardCmt.getCno());
        boardCmtRepository.save(boardCmt);
    }
    @Override
    public void BoardCmtComment(BoardCmtDTO boardCmtDTO,String id) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        int num = 0;
        if(boardCmtDTO.getStep() > 0) {
            num = boardCmtDTO.getCnogroup();
            boardCmtDTO.setIndent(boardCmtDTO.getIndent()+1);
        }
        BoardCmt boardCmt = BoardCmt.builder()
                .bno(boardCmtDTO.getBno())
                .writer(members)
                .content(boardCmtDTO.getContent())
                .step(boardCmtDTO.getStep()+1)
                .indent(boardCmtDTO.getIndent()+1)
                .cnogroup(num)
                .lasttime(null)
                .firsttime(null)
                .build();

        boardCmtRepository.save(boardCmt);

        if(boardCmt.getStep() < 2) {
            boardCmt.CnoGroup(boardCmtDTO.getCno());
            boardCmtRepository.save(boardCmt);
        }
    }
    
    //수정
    public void CmtModify(BoardCmtDTO boardCmtDTO) {
        Optional<BoardCmt> result = boardCmtRepository.findById(new BoardCmtId(boardCmtDTO.getBno().getBno(),boardCmtDTO.getCno()));
        BoardCmt boardCmt = result.orElseThrow();
        boardCmt.change(boardCmtDTO.getContent(),LocalDateTime.now());
        boardCmtRepository.save(boardCmt);
    }

    public void CmtDel(int bno, int cno) {
        boardCmtRepository.deleteById(new BoardCmtId(bno,cno));
    }

    //페이징
    public PageResponseDTO<BoardCmtDTO> BoardCmtList(PageRequestDTO pageRequestDTO,int bno) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <=0? 0: pageRequestDTO.getPage()-1, 100, Sort.by("cno").ascending());

        Page<BoardCmt> result = boardCmtRepository.searchCmt(pageable,bno);
        List<BoardCmtDTO> dtoList = result.getContent().stream().map(boardCmt -> modelMapper.map(boardCmt,BoardCmtDTO.class)).collect(Collectors.toList());
        return PageResponseDTO.<BoardCmtDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
