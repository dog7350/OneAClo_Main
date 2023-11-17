package kr.kro.oneaclo.www.DTO.Board;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BoardFileDTO {
    private int bno;
    private String filename;
}
