package capstone.doAds.dto;

import capstone.doAds.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private Long memberId;
    private String profileUrl;
    private String nickname;
    private String content;

    public MessageDto(Message message) {
        this.memberId = message.getSender().getId();
        this.profileUrl = message.getSender().getProfile().getProfileImageUrl();
        this.nickname = message.getSender().getNickname();
        this.content = message.getContent();
    }
}
