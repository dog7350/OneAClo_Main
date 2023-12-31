package kr.kro.oneaclo.www.Repository.Mypage;


import kr.kro.oneaclo.www.Entity.Mypage.MemberInfo;
import kr.kro.oneaclo.www.Entity.Mypage.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInfoRepository extends JpaRepository<MemberInfo,String> {
    Optional<MemberInfo> findById(String email);
    Optional<MemberInfo> findMemberInfoById(Members id);

    Page<MemberInfo> findAllByOrderByIdAsc(Pageable pageable);
    Page<MemberInfo> findById_IdLikeOrderByIdAsc(Pageable pageable, String id);
    Page<MemberInfo> findByNameLikeOrderByIdAsc(Pageable pageable, String name);
}
