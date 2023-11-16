package capstone.doAds.repository;

import capstone.doAds.domain.YoutubeProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YoutubeProfileRepository extends JpaRepository<YoutubeProfile, Long> {
}
