package capstone.doAds.repository;

import capstone.doAds.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(@Param("name") String name);
}
