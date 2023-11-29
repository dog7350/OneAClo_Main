package kr.kro.oneaclo.www.Service.Board;

import kr.kro.oneaclo.www.DTO.Board.BoardFileDTO;
import org.springframework.web.multipart.MultipartFile;

public interface BoardFileService {
    void FilaNameSave(BoardFileDTO boardFileDTO);
    void FileSave(int bno, MultipartFile multipartFile);
    void FileNameModify(BoardFileDTO dto);
    void FileModify(MultipartFile multipartFile,int bno);
    BoardFileDTO BoardFileInfo(int bno);
}
