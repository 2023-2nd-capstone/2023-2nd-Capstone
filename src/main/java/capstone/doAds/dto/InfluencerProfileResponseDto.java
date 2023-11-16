package capstone.doAds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InfluencerProfileResponseDto {

    Long profileId;
    String nickname;
    String email;
    String profileImageUrl;
    String description;
    List<String> profileTagNames;
    YoutubeProfileDto youtubeProfileDto;

    public InfluencerProfileResponseDto(Long profileId, String nickname, String email, String profileImageUrl, String description, List<String> profileTagNames) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.profileTagNames = profileTagNames;
    }

    public InfluencerProfileResponseDto(Long profileId, String nickname, String email, String profileImageUrl, String description, List<String> profileTagNames, YoutubeProfileDto youtubeProfileDto) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.profileTagNames = profileTagNames;
        this.youtubeProfileDto = youtubeProfileDto;
    }
}
