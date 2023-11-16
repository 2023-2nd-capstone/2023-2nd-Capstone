package capstone.doAds.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Getter
@NoArgsConstructor
public class YoutubeProfileDto {
    String channelName;
    BigInteger subscribeCount;
    String description;

    public YoutubeProfileDto(String channelName, BigInteger subscribeCount, String description) {
        this.channelName = channelName;
        this.subscribeCount = subscribeCount;
        this.description = description;
    }
}
