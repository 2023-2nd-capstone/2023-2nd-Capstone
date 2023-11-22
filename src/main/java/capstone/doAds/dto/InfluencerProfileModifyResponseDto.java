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
    List<String> tags;

    public InfluencerProfileModifyResponseDto(String nickname, String profileImageUrl, String description, List<String> tags) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
        this.tags = tags;
    }

}
