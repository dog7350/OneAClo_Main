package kr.kro.oneaclo.www.Entity.Board.IdClass;

import kr.kro.oneaclo.www.Entity.Board.BoardCmt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class BoardCmtReportId implements Serializable {

    @EqualsAndHashCode.Include
    public BoardCmt boardCmt;

    @EqualsAndHashCode.Include
    public String reporter;
}
