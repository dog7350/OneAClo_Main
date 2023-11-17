package kr.kro.oneaclo.www.Service.Member;


import kr.kro.oneaclo.www.DTO.Member.MemberDTO;

public interface MemberInfoService {
    void MemberInfoJoin(MemberDTO dto);
    boolean EmailCk(String email);
    void PhoneChange(String NewPhone,String email);
    void AddrChange(String email,String zipcode,String address, String detailaddr);
}
