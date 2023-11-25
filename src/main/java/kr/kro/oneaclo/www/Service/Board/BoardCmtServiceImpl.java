package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardCmtDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardCmtId;
import kr.kro.oneaclo.www.Repository.Board.BoardCmtRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCmtServiceImpl implements BoardCmtService{

    private final BoardCmtRepository boardCmtRepository;
    private final ModelMapper modelMapper;
    public void CmtSave(BoardCmtDTO boardCmtDTO) {
        BoardCmt boardCmt = BoardCmt.builder()
                .content(boardCmtDTO.getContent())
                .firsttime(null)
                .lasttime(null)
                .writer(boardCmtDTO.getWriter())
                .bno(boardCmtDTO.getBno())
                .build();

        boardCmtRepository.save(boardCmt);
    }
    public void CmtModify(BoardCmtDTO boardCmtDTO) {
        Optional<BoardCmt> result = boardCmtRepository.findById(new BoardCmtId(boardCmtDTO.getBno().getBno(),boardCmtDTO.getCno()));
        BoardCmt boardCmt = result.orElseThrow();
        boardCmt.change(boardCmtDTO.getContent(),LocalDateTime.now());
        boardCmtRepository.save(boardCmt);
    }

    @Override
    public PageResponseDTO<BoardCmtDTO> Cmtlist(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<BoardCmt> result = boardCmtRepository.searchCmtAll(pageable);
        List<BoardCmtDTO> dtoList = result.getContent().stream().map(boardCmt -> modelMapper.map(boardCmt,BoardCmtDTO.class)).collect(Collectors.toList());

        return PageResponseDTO.<BoardCmtDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
