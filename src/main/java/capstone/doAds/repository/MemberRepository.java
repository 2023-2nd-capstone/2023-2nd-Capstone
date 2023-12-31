package capstone.doAds.repository;

import capstone.doAds.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query("select m FROM Member m JOIN FETCH m.profile p " + "WHERE m.email = :email")
    Optional<Member> findByEmailFetchProfile(@Param("email") String email);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.messageRooms mr " +
            "LEFT JOIN FETCH mr.to mrt LEFT JOIN FETCH mrt.profile WHERE m.email = :email " +
            "ORDER BY mr.lastModifiedDate DESC")
    Optional<Member> findByEmailFetchMessageRoom(@Param("email") String email);
}
