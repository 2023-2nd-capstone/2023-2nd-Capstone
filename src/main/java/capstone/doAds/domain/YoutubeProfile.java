package capstone.doAds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class YoutubeProfile {

    @Id
    @GeneratedValue
    @Column(name = "youtube_profile_id")
    private Long id;

    private String channelName;

    private String channelInfo;

    private Long subscribeCount;

    private String backgroundPhotoUrl;

    @OneToOne(mappedBy = "profile")
    private Profile profile;

}
