package novo.backend_novo.Repository;

import novo.backend_novo.Domain.Star;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StarRepository extends JpaRepository<Star,Long> {

    boolean existsByContentIdAndMemberId(Long contentId, Long memberId);
    @EntityGraph(attributePaths = {"member","content"})
    List<Star> findByContentId(Long contentId);

    @EntityGraph(attributePaths = {"member","content"})
    List<Star> findByMemberId(Long memberId);

    @EntityGraph(attributePaths = {"member","content"})
    List<Star> findByContentIdAndMemberId(Long contentId, Long memberId);

}
