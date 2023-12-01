package kr.kro.oneaclo.www.Entity.Board.IdClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class BoardReportId implements Serializable {

    @EqualsAndHashCode.Include
    public int bno;

    @EqualsAndHashCode.Include
    public String reporter;
}
