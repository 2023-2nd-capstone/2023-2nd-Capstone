package capstone.doAds.domain;

import capstone.doAds.dto.InfluencerProfileModifyResponseDto;
import capstone.doAds.dto.InfluencerProfileResponseDto;
import capstone.doAds.dto.NicknameSearchResponseDto;
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

    @Column(length = 1000)
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
        this.profileImageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMDExMDFfMyAg/MDAxNjA0MjI5NDA4NDMy.5zGHwAo_UtaQFX8Hd7zrDi1WiV5KrDsPHcRzu3e6b8Eg.IlkR3QN__c3o7Qe9z5_xYyCyr2vcx7L_W1arNFgwAJwg.JPEG.gambasg/%EC%9C%A0%ED%8A%9C%EB%B8%8C_%EA%B8%B0%EB%B3%B8%ED%94%84%EB%A1%9C%ED%95%84_%ED%8C%8C%EC%8A%A4%ED%85%94.jpg?type=w800";
    }

    public void setYoutubeProfile(YoutubeProfile youtubeProfile) {
        this.youtubeProfile = youtubeProfile;
        this.profileImageUrl = youtubeProfile.getBackgroundPhotoUrl();
    }

    //--연관관계 메서드--//
    public List<String> getProfileTagNames() {
        List<String> tagNames = new ArrayList<>();
        for (ProfileTag profileTag : this.profileTags) {
            tagNames.add(profileTag.getTagName());
        }
        return tagNames;
    }

    public void addTags(ProfileTag profileTag) {
        this.profileTags.add(profileTag);
    }

    public void resetTags() {
        this.profileTags.clear();
    }


    //-- 서비스 로직--//
    public InfluencerProfileResponseDto getInfluencerProfile(boolean isMine) {
        return new InfluencerProfileResponseDto(
                this.id,
                member.getNickname(),
                member.getEmail(),
                youtubeProfile.getChannelName(),
                youtubeProfile.getBackgroundPhotoUrl(),
                this.getDescription(),
                youtubeProfile.getSubscribeCount(),
                this.getProfileTagNames(),
                isMine);
    }

    public void modifyInfluencerProfile(InfluencerProfileModifyResponseDto influencerProfileModifyResponseDto) {
        this.description = influencerProfileModifyResponseDto.getDescription();
        this.profileImageUrl = influencerProfileModifyResponseDto.getProfileImageUrl();
        member.modifyNickname(influencerProfileModifyResponseDto.getNickname());
    }

    public NicknameSearchResponseDto getNicknameSearch() {
        return new NicknameSearchResponseDto(
                member.getNickname(),
                youtubeProfile.getChannelName(),
                this.getProfileImageUrl(),
                this.getLikeCount(),
                this.getProfileTagNames()
        );
    }

    public Long decreaseLikesCount() {
        return --likeCount;
    }

    public Long increaseLikesCount() {
        return ++likeCount;
    }
}
