package kr.kro.oneaclo.www.Service.Member;


import kr.kro.oneaclo.www.DTO.Member.MemberDTO;
import kr.kro.oneaclo.www.Entity.Member.MemberInfo;
import kr.kro.oneaclo.www.Entity.Member.Members;
import kr.kro.oneaclo.www.Repository.Member.MemberInfoRepository;
import kr.kro.oneaclo.www.Repository.Member.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MembersRepository membersRepository;
    private final MemberInfoRepository memberInfoRepository;
    public void MemberInfoJoin(MemberDTO dto) {

        Optional<Members> result = membersRepository.findById(dto.getId());
        Members members = result.orElseThrow();

        MemberInfo memberInfo = MemberInfo.builder()
                .id(members)
                .gender(dto.getGender())
                .email(dto.getEmail())
                .age(dto.getAge())
                .zipcode(dto.getZipcode())
                .address(dto.getAddress())
                .detailaddr(dto.getDetailaddr())
                .phone(dto.getPhone())
                .name(dto.getName())
                .build();

        memberInfoRepository.save(memberInfo);
    }
    public boolean EmailCk(String email) {
        Optional<MemberInfo> memberInfo = memberInfoRepository.findById(email);
        return memberInfo.isPresent();
    }

    public void PhoneChange(String NewPhone,String email) {
        Optional<MemberInfo> result = memberInfoRepository.findById(email);
        MemberInfo memberInfo = result.orElseThrow();
        memberInfo.PhoneCh(NewPhone);
        memberInfoRepository.save(memberInfo);
    }

    public void AddrChange(String email,String zipcode,String address, String detailaddr) {
        Optional<MemberInfo> result = memberInfoRepository.findById(email);
        MemberInfo memberInfo = result.orElseThrow();
        memberInfo.Addrch(zipcode,address,detailaddr);
        memberInfoRepository.save(memberInfo);
    }

}
