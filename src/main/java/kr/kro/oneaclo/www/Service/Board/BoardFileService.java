package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface BoardFileService {
    void FileTotalSave(BoardFileDTO boardFileDTO,MultipartFile multipartFile);
    void FileTotalModify(BoardFileDTO BeforeFileDTO, BoardFileDTO InputFileDTO, MultipartFile multipartFile);
    BoardFileDTO BoardFileInfo(int bno);
    void BoardFileDel(int bno);
}
