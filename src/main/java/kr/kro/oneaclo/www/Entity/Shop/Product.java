package kr.kro.oneaclo.www.Entity.Shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Table(name = "product")
@Entity
@SequenceGenerator(name = "PRODUCT_GENERATOR", sequenceName = "PRODUCT_SEQ", allocationSize = 1)
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_GENERATOR")
    @Column(name = "pno", nullable = false, updatable = false, unique = true)
    private int pno;

    @Column(name = "bcategory", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String bcategory;

    @Column(name = "mcategory", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String mcategory;

    @Column(name = "scategory", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String scategory;

    @Column(name = "img", columnDefinition = "VARCHAR2(500)")
    private String img;

    @Column(name = "pname", columnDefinition = "VARCHAR2(500)")
    private String pname;

    @Column(name = "price")
    private int price;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "CLOB")
    private String content;

    @Column(name = "time", columnDefinition = "TIMESTAMP")
    @ColumnDefault("SYSDATE")
    private Timestamp time;

    @Column(name = "inquiry")
    @ColumnDefault("0")
    private int inquiry;

    public void ThumbnailChange(String thumbnail) { this.img = thumbnail; }

    public void InquiryAdd() { inquiry++; }
}
