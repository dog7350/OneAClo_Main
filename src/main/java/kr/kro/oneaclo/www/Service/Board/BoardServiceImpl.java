package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageRequestDTO;
import kr.kro.oneaclo.www.DTO.Board.Page.PageResponseDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final MembersRepository membersRepository;
    public List<Board> BoardGetList() {
        return boardRepository.findAll();
    }

    public void BoardSave(String[] BoardUser,String title,String content) {
        Optional<Members> result = membersRepository.findById(BoardUser[0]);
        Members members = result.orElseThrow();
        Board board = Board.builder()
                .writer(members)
                .firsttime(null)
                .lasttime(null)
                .btype(BoardUser[1])
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);
    }
    @Override
    public PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO) {



        return null;
    }

}
