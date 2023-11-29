package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Board.BoardFile;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardFileId;
import kr.kro.oneaclo.www.Repository.Board.BoardFileRepository;
import kr.kro.oneaclo.www.Repository.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService{

    private final BoardFileRepository boardFileRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    @Override
    public void FilaNameSave(BoardFileDTO boardFileDTO) {
        Optional<Board> result = boardRepository.findByBno(boardFileDTO.getBno());
        Board board = result.orElseThrow();
        BoardFile boardFile = BoardFile.builder()
                .bno(board)
                .filename(boardFileDTO.getBno()+"_"+boardFileDTO.getFilename())
                .build();

        boardFileRepository.save(boardFile);
    }

    @Override
    public void FileSave(int bno, MultipartFile multipartFile) {
        File BoardFile = new File(bno+"_"+multipartFile.getOriginalFilename());
        try{
            multipartFile.transferTo(BoardFile);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void FileNameModify(BoardFileDTO dto) {
        Optional<Board> BoardResult = boardRepository.findByBno(dto.getBno());
        Board board = BoardResult.orElseThrow();
        Optional<BoardFile> FileResult = boardFileRepository.findByBno(board);
        BoardFile boardFile = FileResult.orElseThrow();
        if(Objects.equals(dto.getFilename(), "")) {
            boardFileRepository.deleteById(new BoardFileId(board.getBno(),boardFile.getFilename()));
        }else {
            boardFile.FileChange(dto.getBno()+"_"+dto.getFilename());
            boardFileRepository.save(boardFile);
        }
    }
    @Override
    public void FileModify(MultipartFile multipartFile,int bno) {
        Optional<Board> BoardResult = boardRepository.findByBno(bno);
        Board board = BoardResult.orElseThrow();
        Optional<BoardFile> FileResult = boardFileRepository.findByBno(board);
        BoardFile boardFile = FileResult.orElseThrow();
        File Origin = new File(boardFile.getFilename());
        Origin.delete();
        if(!multipartFile.isEmpty()) {
            File NewBoardFile = new File(boardFile.getBno()+"_"+multipartFile.getOriginalFilename());
            try{
                multipartFile.transferTo(NewBoardFile);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public BoardFileDTO BoardFileInfo(int bno) {
        Optional<Board> BoardResult = boardRepository.findByBno(bno);
        Board board = BoardResult.orElseThrow();
        Optional<BoardFile> FileResult = boardFileRepository.findByBno(board);
        if(FileResult.isPresent()) {
            BoardFile boardFile = FileResult.orElseThrow();
            return modelMapper.map(boardFile,BoardFileDTO.class);
        }else {
            return null;
        }
    }
}
