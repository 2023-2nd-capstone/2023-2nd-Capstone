package capstone.doAds.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class YoutubeProfile {

    @Id @GeneratedValue
    @Column(name = "youtube_profile_id")
    private Long id;

    private String channelName;

    private String channelInfo;

    private Long subscribeCount;

    private String backgroundPhotoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;
}
