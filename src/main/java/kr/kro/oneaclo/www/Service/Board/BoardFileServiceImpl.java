package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardFile;
import kr.kro.oneaclo.www.Repository.Board.BoardFileRepository;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService{

    private final BoardFileRepository boardFileRepository;
    private final BoardRepository boardRepository;
    @Override
    public void FilaNameSave(BoardFileDTO boardFileDTO) {
        Optional<Board> result = boardRepository.findByBno(boardFileDTO.getBno());
        Board board = result.orElseThrow();
        BoardFile boardFile = BoardFile.builder()
                .bno(board)
                .filename(boardFileDTO.getFilename())
                .build();

        boardFileRepository.save(boardFile);
    }
}
