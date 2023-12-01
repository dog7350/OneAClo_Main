package kr.kro.oneaclo.www.Service.Mypage;


import kr.kro.oneaclo.www.DTO.Mypage.MemberDTO;
import kr.kro.oneaclo.www.Entity.Mypage.MemberInfo;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import kr.kro.oneaclo.www.Repository.Mypage.MemberInfoRepository;
import kr.kro.oneaclo.www.Repository.Mypage.MembersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberInfoServiceImpl implements MemberInfoService {

    private final MembersRepository membersRepository;
    private final MemberInfoRepository memberInfoRepository;
    //생성
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
    
    //조회
    public boolean EmailCk(String email) {
        Optional<MemberInfo> memberInfo = memberInfoRepository.findById(email);
        return memberInfo.isPresent();
    }
    
    //수정

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

    public Optional<MemberInfo> findUser(String email) { return memberInfoRepository.findById(email); }

    public Page<MemberInfo> AllUserPage(int pageNumber, int elementCount) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);
        return memberInfoRepository.findAllByOrderByIdAsc(pageable);
    }

    public Page<MemberInfo> SearchUserPage(int pageNumber, int elementCount, String searchOption, String searchValue) {
        Pageable pageable = PageRequest.of(pageNumber, elementCount);
        Page<MemberInfo> pages;

        String value = "%" + searchValue + "%";

        if (searchOption.equals("id")) pages = memberInfoRepository.findById_IdLikeOrderByIdAsc(pageable, value);
        else pages = memberInfoRepository.findByNameLikeOrderByIdAsc(pageable, value);

        return pages;
    }

}
