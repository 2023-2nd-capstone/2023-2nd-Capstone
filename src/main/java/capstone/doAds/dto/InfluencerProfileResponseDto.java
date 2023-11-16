package capstone.doAds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InfluencerProfileResponseDto {

    String nickname;
    String email;
    String channelName;
    String profileImageUrl;
    String description;
    List<String> profileTagNames;

    public InfluencerProfileResponseDto(String nickname, String email, String channelName, String profileImageUrl, String description, List<String> profileTagNames) {
        this.nickname = nickname;
        this.email = email;
        this.channelName = channelName;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.profileTagNames = profileTagNames;
    }
}
