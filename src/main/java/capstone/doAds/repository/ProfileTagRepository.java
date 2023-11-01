package capstone.doAds.repository;

import capstone.doAds.domain.ProfileTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileTagRepository extends JpaRepository<ProfileTag, Long> {
}
