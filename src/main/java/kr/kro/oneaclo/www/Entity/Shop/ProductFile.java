package kr.kro.oneaclo.www.Entity.Shop;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Table(name = "productfile")
@IdClass(ProductFileId.class)
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ProductFile {
    @Id
    @ManyToOne
    @JoinColumn(name = "pno", nullable = false, updatable = false, unique = true)
    private Product pno;

    @Id
    @Column(name = "filename", columnDefinition = "VARCHAR2(500)")
    private String filename;
}
