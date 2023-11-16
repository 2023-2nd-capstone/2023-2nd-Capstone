package capstone.doAds.domain;

import capstone.doAds.dto.InfluencerProfileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
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

    private String profileImageUrl;

    private Long likeCount = 0l;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "youtube_profile_id")
    private YoutubeProfile youtubeProfile;

    @OneToOne(mappedBy = "profile", fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProfileTag> profileTags = new ArrayList<>();

    //--연관관계 메서드--//
    private List<String> getProfileTagNames() {
        List<String> tagNames = new ArrayList<>();
        for (ProfileTag profileTag : this.profileTags) {
            tagNames.add(profileTag.getTagName());
        }
        return tagNames;
    }


    //-- 서비스 로직--//
    public InfluencerProfileResponseDto getInfluencerProfile() {
        return new InfluencerProfileResponseDto(
                member.getNickname(),
                member.getEmail(),
                youtubeProfile.getChannelName(),
                youtubeProfile.getBackgroundPhotoUrl(),
                this.getDescription(),
                this.getProfileTagNames());
    }
}
