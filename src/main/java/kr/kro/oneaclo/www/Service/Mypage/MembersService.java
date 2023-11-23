package kr.kro.oneaclo.www.Service.Mypage;


import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface MembersService {
    String MembersJoin(MemberDTO dto,MultipartFile UserProfile);
    boolean IdCk(String UserId);
    void send(String email,String title,String body);
    void PwChange(String ChangePw,String id);
    void NickChange(String id,String NickName);
    void ProfileChange(String id,MultipartFile NewProfile);
    void UserDel(String id);

    boolean UserCk(String UserId,String UserPw);

    Page<Members> AllUserPage(int pageNumber, int elementCount);

    void AuthChange(String id, String auth);
    void ActiveChange(String id, String active);
}
