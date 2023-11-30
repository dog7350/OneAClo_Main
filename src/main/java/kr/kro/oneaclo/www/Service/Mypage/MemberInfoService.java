package kr.kro.oneaclo.www.Service.Mypage;


import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.Entity.Mypage.MemberInfo;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface MemberInfoService {
    void MemberInfoJoin(MemberDTO dto);
    boolean EmailCk(String email);
    void PhoneChange(String NewPhone,String email);
    void AddrChange(String email,String zipcode,String address, String detailaddr);

    Optional<MemberInfo> findUser(String email);
    Page<MemberInfo> AllUserPage(int pageNumber, int elementCount);
    Page<MemberInfo> SearchUserPage(int pageNumber, int elementCount, String searchOption, String searchValue);
}
