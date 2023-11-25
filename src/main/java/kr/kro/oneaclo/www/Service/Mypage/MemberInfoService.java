package kr.kro.oneaclo.www.Service.Mypage;


import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;

public interface MemberInfoService {
    void MemberInfoJoin(MemberDTO dto);
    boolean EmailCk(String email);
    void PhoneChange(String NewPhone,String email);
    void AddrChange(String id,String zipcode,String address, String detailaddr);
}
