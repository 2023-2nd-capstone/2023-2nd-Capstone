package capstone.doAds.domain;

import capstone.doAds.dto.InfluencerProfileModifyResponseDto;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.dto.YoutubeProfileDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
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

    public Profile() {
        this.description = "프로필 설명을 수정해주세요!";
        this.likeCount = 0l;
    }

    public void setYoutubeProfile(YoutubeProfile youtubeProfile) {
        this.youtubeProfile = youtubeProfile;
    }

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
        if (youtubeProfile == null) {
            return new InfluencerProfileResponseDto(
                    this.id,
                    member.getNickname(),
                    member.getEmail(),
                    this.profileImageUrl,
                    this.getDescription(),
                    this.getProfileTagNames());
        }
        return new InfluencerProfileResponseDto(
                this.id,
                member.getNickname(),
                member.getEmail(),
                this.profileImageUrl,
                this.getDescription(),
                this.getProfileTagNames(),
                getYoutubeProfile());
    }

    public YoutubeProfileDto getYoutubeProfile() {
        return new YoutubeProfileDto(youtubeProfile.getChannelName(), youtubeProfile.getSubscribeCount(), youtubeProfile.getChannelInfo());
    }

    public void modifyInfluencerProfile(InfluencerProfileModifyResponseDto influencerProfileModifyResponseDto) {
        this.description = influencerProfileModifyResponseDto.getDescription();
        this.profileImageUrl = influencerProfileModifyResponseDto.getProfileImageUrl();
        member.modifyNickname(influencerProfileModifyResponseDto.getNickname());
    }
}
