package kr.kro.oneaclo.www.Entity.Mypage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@Table(name = "members")
@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Members {

    @Id
    @Column(name = "id",nullable = false,updatable = false,unique = true,columnDefinition = "varchar2(50)")
    private String id;

    @Column(name = "pw",nullable = false,columnDefinition = "varchar2(50)")
    private String pw;

    @Column(name= "nick",nullable = false,columnDefinition = "varchar2(50)")
    private String nick;

    @Column(name = "profile",columnDefinition = "varchar2(500)")
    @ColumnDefault("'D_profile.jpg'")
    private String profile;

    @Column(name = "auth",columnDefinition = "varchar2(50)")
    @ColumnDefault("'c'")
    private String auth;

    @Column(name= "active",columnDefinition = "varchar2(50)")
    @ColumnDefault("'a'")
    private String active;

    public void PwChange(String pw) {
        this.pw = pw;
    }
    public void NickChange(String nick) {
        this.nick = nick;
    }
    public void ProfileChange(String profile) {
        this.profile = profile;
    }

    public void admin(String auth) {
        this.auth = auth;
    }
}
