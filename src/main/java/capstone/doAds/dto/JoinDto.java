package capstone.doAds.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JoinDto {
    private String nickname;
    private String email;
    private String password;
}
