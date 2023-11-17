package kr.kro.oneaclo.www.Repository.Mypage;


import kr.kro.oneaclo.www.Entity.Mypage.MemberInfo;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo,String> {
    Optional<MemberInfo> findById(String email);
    Optional<MemberInfo> findMemberInfoById(Members id);
}
