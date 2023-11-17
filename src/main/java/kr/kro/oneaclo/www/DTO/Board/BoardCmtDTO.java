package kr.kro.oneaclo.www.DTO.Board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class BoardCmtDTO {
    private int bno;
    private int cno;
    private String writer;
    private String content;
    private LocalDateTime firsttime;
    private LocalDateTime lasttime;
    private int cnogroup;
    private int step;
    private int indent;
}
