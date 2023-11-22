package capstone.doAds.service;

import capstone.doAds.domain.Likes;
import capstone.doAds.domain.Member;
import capstone.doAds.domain.Profile;
import capstone.doAds.exception.NotFoundException;
import capstone.doAds.exception.UnauthorizedException;
import capstone.doAds.repository.LikesRepository;
import capstone.doAds.repository.MemberRepository;
import capstone.doAds.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static capstone.doAds.auth.SecurityUtils.getLoggedUserEmail;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {

    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final LikesRepository likesRepository;

    @Transactional
    public boolean like(Long profileId) {
        Member currentMember = memberRepository.findByEmail(getLoggedUserEmail()).orElseThrow(
                () -> new UnauthorizedException("로그인이 필요합니다."));
        Profile profile = profileRepository.findById(profileId).orElseThrow(
                () -> new NotFoundException("존재하지 않는 프로필입니다."));

        Optional<Likes> likes = likesRepository.findByMemberAndProfile(currentMember, profile);
        if(likes.isPresent()) {
            profile.decreaseLikesCount();
            likesRepository.delete(likes.get());
            return false;
        }
        likesRepository.save(Likes.builder().member(currentMember).profile(profile).build());
        return true;
    }
}
