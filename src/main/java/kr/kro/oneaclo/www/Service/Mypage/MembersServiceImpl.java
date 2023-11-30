package kr.kro.oneaclo.www.Service.Mypage;

import jakarta.mail.internet.MimeMessage;
import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MembersServiceImpl implements MembersService{

    private final MembersRepository membersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender mailSender;

    public String MembersJoin(MemberDTO dto,MultipartFile UserProfile) {
        if(!UserProfile.isEmpty()) {
            String[] result = Objects.requireNonNull(UserProfile.getOriginalFilename()).split("\\.");
            dto.setProfile(dto.getId()+"_Profile."+result[1]);
            File NameFilter = new File(dto.getProfile());
            try{
                UserProfile.transferTo(NameFilter);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            dto.setProfile("D_Profile.jpg");
        }

        Members members = Members.builder()
                .id(dto.getId())
                .pw(bCryptPasswordEncoder.encode(dto.getPw()))
                .nick(dto.getNick())
                .profile(dto.getProfile())
                .build();
        membersRepository.save(members);

        return "success";
    }

    //조회
    public boolean IdCk(String UserId) {
        Optional<Members> members = membersRepository.findById(UserId);
        return members.isPresent();
    }

    public void send(String email,String title,String body) {
        MimeMessage msg = mailSender.createMimeMessage();
        try {
            MimeMessageHelper mailHelper = new MimeMessageHelper(msg,true,"utf-8");
            mailHelper.setTo(email);
            mailHelper.setSubject(title);
            mailHelper.setText(body,true);

        }catch (Exception e) {
            e.printStackTrace();
        }
        mailSender.send(msg);
    }

    // 수정
    public void PwChange(String ChangePw,String id) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        members.PwChange(bCryptPasswordEncoder.encode(ChangePw));
        membersRepository.save(members);
    }

    public void NickChange(String id,String NickName) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        members.NickChange(NickName);
        membersRepository.save(members);
    }

    public  void ProfileChange(String id,MultipartFile NewProfile) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        File Origin = new File("C:\\UserProfile\\"+members.getProfile());
        Origin.delete();
        if(!NewProfile.isEmpty()) {
            String[] FileName = Objects.requireNonNull(NewProfile.getOriginalFilename()).split("\\.");
            members.ProfileChange(id+"_Profile."+FileName[1]);
            File NameFilter = new File(members.getProfile());
            try{
                NewProfile.transferTo(NameFilter);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            members.ProfileChange("D_Profile.jpg");
        }
        membersRepository.save(members);
    }
    // 삭제
    public void UserDel(String id) {
        membersRepository.deleteById(id);
    }

    public boolean UserCk(String UserId,String UserPw) {
        Optional<Members> result = membersRepository.findById(UserId);
        Members members = result.orElseThrow();
        if(bCryptPasswordEncoder.matches(UserPw,members.getPw())) {
            return true;
        }
            return false;
    }

    public void AuthChange(String id, String auth) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        members.AuthChange(auth);
        membersRepository.save(members);
    }

    public void ActiveChange(String id, String active) {
        Optional<Members> result = membersRepository.findById(id);
        Members members = result.orElseThrow();
        members.ActiveChange(active);
        membersRepository.save(members);
    }
}
