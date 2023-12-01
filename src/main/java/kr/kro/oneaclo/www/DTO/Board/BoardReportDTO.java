package kr.kro.oneaclo.www.DTO.Board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class BoardReportDTO {
    private int bno;
    private int cno;
    private String reporter;
}
