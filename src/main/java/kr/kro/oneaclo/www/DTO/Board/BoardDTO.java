package kr.kro.oneaclo.www.DTO.Board;


import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class BoardDTO {
    private int bno;
    private Members writer;
    private String title;
    private String content;
    private LocalDateTime firsttime;
    private LocalDateTime lasttime;
    private int inquiry;
    private String btype;
    private int bnogroup;
    private int step;
    private int indent;
}
