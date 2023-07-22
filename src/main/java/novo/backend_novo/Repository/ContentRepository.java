package novo.backend_novo.Repository;

import novo.backend_novo.Domain.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
