package capstone.doAds.dto;

import capstone.doAds.domain.Profile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class FeedDto {
    Long profileId;
    String imageUrl;
    String nickname;
    ArrayList<String> tagNames;

    public FeedDto(Profile profile) {
        this.profileId = profile.getId();
        this.imageUrl = profile.getProfileImageUrl();
        this.nickname = profile.getMember().getNickname();
        this.tagNames = (ArrayList<String>) profile.getProfileTagNames();
    }
}
