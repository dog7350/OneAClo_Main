package kr.kro.oneaclo.www.Entity.Shop;

import jakarta.persistence.*;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;
import java.util.Date;

@Table(name = "productcmt")
@IdClass(ProductCmtId.class)
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SequenceGenerator(name = "CmtSeq",sequenceName = "productcmt_seq",initialValue = 1,allocationSize = 1)
public class ProductCmt {
    @Id
    @ManyToOne
    @JoinColumn(name = "pno", nullable = false, updatable = false, unique = true)
    private Product pno;

    @Id
    @Column(name = "cno", nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "CmtSeq")
    private int cno;

    @ManyToOne
    @JoinColumn(name = "writer", nullable = false, updatable = false, columnDefinition = "VARCHAR2(50)")
    private Members writer;

    @Lob
    @Column(name = "ccontent", nullable = false, columnDefinition = "CLOB")
    private String ccontent;

    @Column(name = "ctime", columnDefinition = "TIMESTAMP")
    @ColumnDefault("SYSDATE")
    private Timestamp ctime;

    @Column(name = "secret", columnDefinition = "VARCHAR2(50)")
    private String secret;

    @Column(name = "cnogroup", columnDefinition = "NUMBER(4)")
    @ColumnDefault("0")
    private int cnogroup;

    @Column(name = "step", columnDefinition = "NUMBER(4)")
    @ColumnDefault("0")
    private int step;

    @Column(name = "indent", columnDefinition = "NUMBER(4)")
    @ColumnDefault("0")
    private int indent;

    public void CnoGroupSave(int cno,String Secret) {
        Date date = new Date();
        this.ctime = new Timestamp(date.getTime());
        this.cnogroup = cno;
        this.secret = Secret;
    }

    public void Cmtmodify(String ccontent,String Secret) {
        this.ccontent = ccontent;
        Date date = new Date();
        this.ctime = new Timestamp(date.getTime());
        this.secret = Secret;
    }

}
