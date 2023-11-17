package kr.kro.oneaclo.www.Repository.Member;


import kr.kro.oneaclo.www.Entity.Member.Members;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembersRepository extends JpaRepository<Members,String> {
    Optional<Members> findById(String id);
    void deleteById(String id);
}
