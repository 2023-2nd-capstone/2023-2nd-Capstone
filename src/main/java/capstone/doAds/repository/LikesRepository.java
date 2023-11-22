package capstone.doAds.repository;

import capstone.doAds.domain.Likes;
import capstone.doAds.domain.Member;
import capstone.doAds.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Query("SELECT l FROM Likes l JOIN FETCH l.profile JOIN FETCH l.member " +
            "WHERE l.profile = :profile AND l.member = :member")
    Optional<Likes> findByMemberAndProfile(Member currentMember, Profile profile);
}
