package capstone.doAds.repository;

import capstone.doAds.domain.Likes;
import capstone.doAds.domain.Member;
import capstone.doAds.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("SELECT l FROM Likes l JOIN FETCH l.profile JOIN FETCH l.member " +
            "WHERE l.profile = :profile AND l.member = :member")
    Optional<Likes> findByMemberAndProfile(@Param("member") Member currentMember, @Param("profile") Profile profile);
}
