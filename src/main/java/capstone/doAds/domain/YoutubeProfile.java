package capstone.doAds.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;

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

    private BigInteger subscribeCount;

    private String backgroundPhotoUrl;

    @OneToOne(mappedBy = "youtubeProfile")
    private Profile profile;

    public YoutubeProfile(String channelName, String channelInfo, BigInteger subscribeCount, String backgroundPhotoUrl) {
        this.channelName = channelName;
        this.channelInfo = channelInfo;
        this.subscribeCount = subscribeCount;
        this.backgroundPhotoUrl = backgroundPhotoUrl;
    }

}
