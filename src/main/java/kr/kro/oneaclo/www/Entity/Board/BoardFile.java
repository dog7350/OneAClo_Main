package kr.kro.oneaclo.www.Entity.Board;

import jakarta.persistence.*;
import kr.kro.oneaclo.www.Entity.Board.IdClass.BoardFileId;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Table(name = "boardfile")
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
@Builder
@IdClass(BoardFileId.class)
public class BoardFile {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bno",columnDefinition = "number(38)",nullable = false)
    private Board bno;

    @Id
    @Column(name = "filename",columnDefinition = "varchar2(500)",nullable = false)
    private String filename;

}
