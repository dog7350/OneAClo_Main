package kr.kro.oneaclo.www.Service.Mypage;


import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import org.springframework.web.multipart.MultipartFile;

public interface MembersService {
    String MembersJoin(MemberDTO dto,MultipartFile UserProfile);
    boolean IdCk(String UserId);
    void send(String email,String title,String body);
    void PwChange(String ChangePw,String id);
    void NickChange(String id,String NickName);
    void ProfileChange(String id,MultipartFile NewProfile);
    void UserDel(String id);

    boolean UserCk(String UserId,String UserPw);
}
