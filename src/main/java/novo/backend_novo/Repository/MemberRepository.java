package novo.backend_novo.Repository;

import novo.backend_novo.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    //회원 중복 방지
    boolean existsByEmail(String email);
}
