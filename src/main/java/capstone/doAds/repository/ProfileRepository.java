package capstone.doAds.repository;

import capstone.doAds.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT DISTINCT p FROM Profile p JOIN FETCH p.member pm LEFT JOIN FETCH p.profileTags pt " +
            "WHERE pm.nickname LIKE %:nickname% AND pm.authority = 'ROLE_INFLUENCER'")
    List<Profile> findAllByNickname(@Param("nickname") String nickname);

    @Query("SELECT p FROM Profile p ORDER BY p.likeCount DESC")
    List<Profile> findProfileByPopular();

    @Query("SELECT DISTINCT p FROM Profile p JOIN FETCH p.profileTags pt ")
    List<Profile> findProfileByTagName(@Param("tagName")String tagName);
}
