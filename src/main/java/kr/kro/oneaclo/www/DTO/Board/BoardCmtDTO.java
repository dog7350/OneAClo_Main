package kr.kro.oneaclo.www.DTO.Board;

import kr.kro.oneaclo.www.Entity.Board.Board;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class BoardCmtDTO {
    private Board bno;
    private int cno;
    private Members writer;
    private String content;
    private LocalDateTime firsttime;
    private LocalDateTime lasttime;
    private int cnogroup;
    private int step;
    private int indent;
}
