package capstone.doAds.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileResponseDto {
    Long profileId;
    String nickname;
    String email;
    String profileImageUrl;
    String description;

    public ProfileResponseDto(Long profileId, String nickname, String email, String profileImageUrl, String description) {
        this.profileId = profileId;
        this.nickname = nickname;
        this.email = email;
        this.profileImageUrl = profileImageUrl;
        this.description = description;
    }
}
