package kr.kro.oneaclo.www.Entity.Mypage;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

@Table(name = "memberinfo")
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class MemberInfo {

    @Id
    @Column(name = "email",nullable = false,columnDefinition = "varchar2(500)")
    private String email;

    @ManyToOne
    @JoinColumn(name = "id",columnDefinition = "varchar2(50)",unique = true)
    private Members id;

    @Column(name = "name",nullable = false,columnDefinition = "varchar2(50)")
    private String name;

    @Column(name = "age",nullable = false)
    private int age;

    @Column(name = "gender",nullable = false,columnDefinition = "varchar2(50)")
    private String gender;

    @Column(name = "phone",nullable = false,columnDefinition = "varchar2(50)")
    private String phone;

    @Column(name = "zipcode",nullable = false,columnDefinition = "varchar2(50)")
    private String zipcode;

    @Column(name = "address",nullable = false,columnDefinition = "varchar2(500)")
    private String address;

    @Column(name = "detailaddr",columnDefinition = "varchar2(500)")
    private String detailaddr;

    public void PhoneCh(String phone) {
        this.phone = phone;
    }
    public void Addrch(String zipcode,String address,String detailaddr) {
        this.zipcode = zipcode;
        this.address = address;
        this.detailaddr = detailaddr;
    }
}
