package kr.kro.oneaclo.www.Entity.Shop;

import jakarta.persistence.*;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Timestamp;

@Table(name = "orders")
@Entity
@SequenceGenerator(name = "ORDERS_GENERATOR", sequenceName = "ORDERS_SEQ", allocationSize = 1)
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_GENERATOR")
    @Column(name = "ono", nullable = false, updatable = false, unique = true)
    private int ono;

    @ManyToOne
    @JoinColumn(name = "pno", nullable = false, unique = true)
    private Product pno;

    @ManyToOne
    @JoinColumn(name = "oid", nullable = false, columnDefinition = "VARCHAR2(50)")
    private Members oid;

    @Column(name = "email", nullable = false, columnDefinition = "varchar2(500)")
    private String email;

    @Column(name = "ocount")
    @ColumnDefault("1")
    private int ocount;

    @Column(name = "totalprice")
    private int totalprice;

    @Column(name = "otime", columnDefinition = "TIMESTAMP")
    @ColumnDefault("SYSDATE")
    private Timestamp otime;

    @Column(name = "ostatus", columnDefinition = "VARCHAR2(50)")
    @ColumnDefault("Checking")
    private String ostatus;

    @Column(name = "ouid", columnDefinition = "varchar2(500)")
    private String ouid;

    @Column(name = "receiver", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String receiver;

    @Column(name = "phone",nullable = false,columnDefinition = "varchar2(50)")
    private String phone;

    @Column(name = "zipcode", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String zipcode;

    @Column(name = "address", nullable = false, columnDefinition = "VARCHAR2(50)")
    private String address;

    @Column(name = "detailaddr", columnDefinition = "VARCHAR2(500)")
    private String detailaddr;

    public void StatusChange(String status) { ostatus = status; }
}
