package capstone.doAds.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class NicknameSearchResponseDto {
    private String nickname;
    private String channelName;
    private String profileImageUrl;
    private Long likeCount;
    private List<String> tagNames;
    private boolean isLike;


    public NicknameSearchResponseDto(String nickname, String channelName, String profileImageUrl, Long likeCount, List<String> tagNames) {
        this.nickname = nickname;
        this.channelName = channelName;
        this.profileImageUrl = profileImageUrl;
        this.likeCount = likeCount;
        this.tagNames = tagNames;
        isLike = false;
    }
}
