package capstone.doAds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class InfluencerProfileModifyResponseDto {
    String nickname;
    String profileImageUrl;
    String description;

    public InfluencerProfileModifyResponseDto(String nickname, String profileImageUrl, String description) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }

}
