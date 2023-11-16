package capstone.doAds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Profile {
    @Id
    @GeneratedValue
    @Column(name = "profile_id")
    private Long id;

    private String description;

    private Long likeCount = 0l;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "youtube_profile_id")
    private YoutubeProfile youtubeProfile;

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
    private Member member;
}
