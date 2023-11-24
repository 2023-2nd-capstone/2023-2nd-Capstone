package capstone.doAds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
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
    boolean isMine;
    Long likeCount;

    public InfluencerProfileResponseDto(Long profileId, String nickname, String email, String channelName, String profileImageUrl, String description,
                                        BigInteger subscribeCount, List<String> profileTagNames, boolean isMine, Long likeCount) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.email = email;
        this.channelName = channelName;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.subscribeCount = subscribeCount;
        this.profileTagNames = profileTagNames;
        this.isMine = isMine;
        this.likeCount = likeCount;
    }
}
