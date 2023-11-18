package kr.kro.oneaclo.www.Entity.Board.IdClass;


import kr.kro.oneaclo.www.Entity.Board.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Data
public class BoardCmtId implements Serializable {

    @EqualsAndHashCode.Include
    public int bno;

    @EqualsAndHashCode.Include
    public int cno;
}
