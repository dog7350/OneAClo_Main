package kr.kro.oneaclo.www.DTO.Mypage;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MemberDTO {

    //Members
    private String id;
    private String pw;
    private String nick;
    private String profile;
    private String auth;
    private String activce;

    //MemberInfo
    private String name;
    private int age;
    private String gender;
    private String email;
    private String phone;
    private String zipcode;
    private String address;
    private String detailaddr;

}
