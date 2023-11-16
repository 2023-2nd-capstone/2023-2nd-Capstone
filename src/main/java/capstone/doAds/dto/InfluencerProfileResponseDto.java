package capstone.doAds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

@Getter
@NoArgsConstructor
public class InfluencerProfileResponseDto {

    Long profileId;
    String nickname;
    String email;
    String channelName;
    String profileImageUrl;
    BigInteger subscribeCount;
    String description;
    List<String> profileTagNames;

    public InfluencerProfileResponseDto(Long profileId, String nickname, String email, String channelName, String profileImageUrl, String description, BigInteger subscribeCount, List<String> profileTagNames) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.email = email;
        this.channelName = channelName;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.subscribeCount = subscribeCount;
        this.profileTagNames = profileTagNames;
    }
}
