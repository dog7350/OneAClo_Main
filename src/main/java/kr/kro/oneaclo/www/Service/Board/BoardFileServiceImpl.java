package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.Common.FileUtils;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {

    private final BoardFileRepository boardFileRepository;
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final FileUtils fileUtils;

    private String FileNameCoding(int bno, String Origin) {
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyHHddHHmmssSS"));
        String fileName = "Board_" + bno + "_" + today + "_" + Origin;
        return fileName;
    }

    //생성
    @Override
    public void FileTotalSave(BoardFileDTO boardFileDTO, MultipartFile multipartFile) {
        Optional<Board> result = boardRepository.findByBno(boardFileDTO.getBno());
        String fileName = FileNameCoding(boardFileDTO.getBno(), multipartFile.getOriginalFilename());

        Board board = result.orElseThrow();
        BoardFile boardFile = BoardFile.builder()
                .bno(board)
                .filename(fileName)
                .build();
        boardFileRepository.save(boardFile);

        fileUtils.BoardFileUpload(multipartFile, fileName);
    }


    //수정
    @Override
    public void FileTotalModify(BoardFileDTO BeforeFileDTO, BoardFileDTO InputFileDTO, MultipartFile multipartFile) {
        Optional<Board> BoardResult = boardRepository.findByBno(InputFileDTO.getBno());
        Board board = BoardResult.orElseThrow();
        Optional<BoardFile> FileResult = boardFileRepository.findById(new BoardFileId(board, BeforeFileDTO.getFilename()));
        BoardFile boardFile = FileResult.orElseThrow();
        File Origin = new File(fileUtils.path+"/file/"+boardFile.getFilename());
        Origin.delete();
        if (Objects.equals(InputFileDTO.getFilename(), "")) {
            boardFileRepository.deleteById(new BoardFileId(board, BeforeFileDTO.getFilename()));
        } else {
            if (!multipartFile.isEmpty()) {
                boardFileRepository.deleteById(new BoardFileId(board, BeforeFileDTO.getFilename()));
                String fileName = FileNameCoding(board.getBno(), multipartFile.getOriginalFilename());

                BoardFile ModifyFile = BoardFile.builder()
                        .bno(board)
                        .filename(fileName)
                        .build();
                boardFileRepository.save(ModifyFile);

                fileUtils.BoardFileUpload(multipartFile, fileName);
            }
        }
    }

    //조회
    @Override
    public BoardFileDTO BoardFileInfo(int bno) {
        Optional<Board> BoardResult = boardRepository.findByBno(bno);
        Board board = BoardResult.orElseThrow();
        Optional<BoardFile> FileResult = boardFileRepository.findByBno(board);
        if (FileResult.isPresent()) {
            BoardFile boardFile = FileResult.orElseThrow();
            return modelMapper.map(boardFile, BoardFileDTO.class);
        } else {
            return null;
        }
    }

    @Override
    public void BoardFileDel(int bno) {
        Optional<Board> BoardResult = boardRepository.findByBno(bno);
        Board board = BoardResult.orElseThrow();
        Optional<BoardFile> FileResult = boardFileRepository.findByBno(board);
        if(FileResult.isPresent()) {
            BoardFile boardFile = FileResult.orElseThrow();
            boardFileRepository.deleteById(new BoardFileId(board, boardFile.getFilename()));
            fileUtils.BoardFileDelete(boardFile.getFilename());
        }
    }
}
