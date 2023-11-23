package kr.kro.oneaclo.www.Repository.Mypage;


import kr.kro.oneaclo.www.Entity.Mypage.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members,String> {
    Optional<Members> findById(String id);
    void deleteById(String id);

    Page<Members> findAllByOrderByIdAsc(Pageable pageable);
}
