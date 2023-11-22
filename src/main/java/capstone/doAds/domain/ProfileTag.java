package capstone.doAds.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class ProfileTag {

    @Id
    @GeneratedValue
    @Column(name = "profile_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    //-- 연관관계 메서드 --//
    public String getTagName() {
        return tag.getName();
    }

    public ProfileTag(Profile profile, Tag tag) {
        this.profile = profile;
        this.tag = tag;
        profile.addTags(this);
    }
}
