package capstone.doAds.config;

import capstone.doAds.domain.*;
import capstone.doAds.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final YoutubeProfileRepository youtubeProfileRepository;
    private final ProfileTagRepository profileTagRepository;
    private final LikesRepository likesRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Tag 뷰티 = tagRepository.save(new Tag("뷰티"));
        Tag 게임 = tagRepository.save(new Tag("게임"));
        Tag 경제 = tagRepository.save(new Tag("경제"));
        Tag it = tagRepository.save(new Tag("IT"));
        Tag 패션 = tagRepository.save(new Tag("패션"));
        Tag 요리 = tagRepository.save(new Tag("요리"));
        Tag 여행 = tagRepository.save(new Tag("여행"));
        Tag 자동차 = tagRepository.save(new Tag("자동차"));
        Tag 운동 = tagRepository.save(new Tag("운동/건강"));
        Tag 연예인 = tagRepository.save(new Tag("연예인/BJ"));
        Tag 음악 = tagRepository.save(new Tag("음악"));
        Tag 먹방 = tagRepository.save(new Tag("먹방"));
        Tag 동물 = tagRepository.save(new Tag("애완/반려동물"));
        Tag 육아 = tagRepository.save(new Tag("키즈/어린이"));

        String dummy = bCryptPasswordEncoder.encode("dummy");
        Profile J_Fla_P = new Profile("음악 유튜버 제이플라입니다.", "https://yt3.googleusercontent.com/UKbftWD63Uw2cDaEV8_k9cgjfStGWQgonuxyLNMun5GKSO0UQfEyXwnaVI7bqUYrEizDKG9aUNM=s176-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("J.Fla", "음악 유튜버 제이플라입니다.", new BigInteger("17500000"), "https://yt3.googleusercontent.com/UKbftWD63Uw2cDaEV8_k9cgjfStGWQgonuxyLNMun5GKSO0UQfEyXwnaVI7bqUYrEizDKG9aUNM=s176-c-k-c0x00ffffff-no-rj"));
        J_Fla_P.addTags(new ProfileTag(J_Fla_P, 음악));
        profileRepository.save(J_Fla_P);
        Member J_Fla = new Member("dummy1@dummy", dummy, "J.Fla", Authority.ROLE_INFLUENCER, J_Fla_P);
        memberRepository.save(J_Fla);

        Profile 김한용_P = new Profile("자동차 유튜버 김한용입니다.", "https://yt3.ggpht.com/ytc/APkrFKbGtWEHhGI5T19QBU55jNFmehoJXutk8GjnX7xSdg=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("김한용의 MOCAR", "자동차 유튜버 제이플라입니다.", new BigInteger("1080000"), "https://yt3.ggpht.com/ytc/APkrFKbGtWEHhGI5T19QBU55jNFmehoJXutk8GjnX7xSdg=s240-c-k-c0x00ffffff-no-rj"));
        김한용_P.addTags(new ProfileTag(김한용_P, 자동차));
        profileRepository.save(김한용_P);
        Member 김한용 = new Member("dummy2@dummy", "dummy", "김한용", Authority.ROLE_INFLUENCER, 김한용_P);
        memberRepository.save(김한용);

        Profile ITSub잇섭 = new Profile("IT 유튜버 ITSub잇섭 입니다.", "https://yt3.ggpht.com/ytc/APkrFKYQd0qp4APkQrgHyjDnuDODqHPbVsWiEBMGTfUNlg=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("ITSub잇섭", "IT 유튜버 ITsub잇섭입니다.", new BigInteger("2450000"), "https://yt3.ggpht.com/ytc/APkrFKYQd0qp4APkQrgHyjDnuDODqHPbVsWiEBMGTfUNlg=s240-c-k-c0x00ffffff-no-rj"));
        ITSub잇섭.addTags(new ProfileTag(ITSub잇섭, it));
        profileRepository.save(ITSub잇섭);
        Member 잇섭 = new Member("dummy3@dummy", "dummy", "ITSub잇섭", Authority.ROLE_INFLUENCER, ITSub잇섭);
        memberRepository.save(잇섭);

        Profile 감스트 = new Profile("게임 유튜버 감스트입니다.", "https://yt3.ggpht.com/AApD7o2ClJqIxEJijGBpqiRWLuH_L6UP_XOtQWB17mn72blCZC5fiIvfvjqmbmcECWOVBdy3Lg=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("감스트GAMST", "게임 유튜버 감스트입니다.", new BigInteger("2430000"), "https://yt3.ggpht.com/AApD7o2ClJqIxEJijGBpqiRWLuH_L6UP_XOtQWB17mn72blCZC5fiIvfvjqmbmcECWOVBdy3Lg=s240-c-k-c0x00ffffff-no-rj"));
        감스트.addTags(new ProfileTag(감스트, 게임));
        profileRepository.save(감스트);
        Member 감스트1 = new Member("dummy4@dummy", "dummy", "감스트", Authority.ROLE_INFLUENCER, 감스트);
        memberRepository.save(감스트1);

        Profile PONY_P = new Profile("뷰티 유튜버 PONY입니다.", "https://yt3.ggpht.com/rRRhOgw6RgI_hqA5VAbBCTTWKpc2MoSelLfz1_5b7SOO7MAiKCK_KE_wbyNhgH3HaDtdOhChu50=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("PONY Syndrome", "뷰티 유튜버 PONY입니다.", new BigInteger("5960000"), "https://yt3.ggpht.com/rRRhOgw6RgI_hqA5VAbBCTTWKpc2MoSelLfz1_5b7SOO7MAiKCK_KE_wbyNhgH3HaDtdOhChu50=s240-c-k-c0x00ffffff-no-rj"));
        PONY_P.addTags(new ProfileTag(PONY_P, 뷰티));
        profileRepository.save(PONY_P);
        Member PONY = new Member("dummy5@dummy", "dummy", "PONY", Authority.ROLE_INFLUENCER, PONY_P);
        memberRepository.save(PONY);

        Profile 삼프로TV_P = new Profile("경제 유튜버 삼프로TV입니다.", "https://yt3.ggpht.com/anT2TJY_rss5WExUZ8QFW-G4z-3paeISZx5urUY_MRuX7OWNFPgd8tmT0xfJugn6utufJvegJA=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("삼프로TV", "경제 유튜버 삼프로TV입니다.", new BigInteger("2350000"), "https://yt3.ggpht.com/anT2TJY_rss5WExUZ8QFW-G4z-3paeISZx5urUY_MRuX7OWNFPgd8tmT0xfJugn6utufJvegJA=s240-c-k-c0x00ffffff-no-rj"));
        삼프로TV_P.addTags(new ProfileTag(삼프로TV_P, 경제));
        profileRepository.save(삼프로TV_P);
        Member 삼프로TV = new Member("dummy6@dummy", "dummy", "삼프로TV", Authority.ROLE_INFLUENCER, 삼프로TV_P);
        memberRepository.save(삼프로TV);

        Profile 최겨울_P = new Profile("패션 유튜버 최겨울입니다.", "https://yt3.ggpht.com/ytc/APkrFKaLaX1zTI2UZJ2RmVUvVBHjD9Woo4v8lqxzHi0mWQ=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("스타일가이드 최겨울", "패션 유튜버 최겨울입니다.", new BigInteger("691000"), "https://yt3.ggpht.com/ytc/APkrFKaLaX1zTI2UZJ2RmVUvVBHjD9Woo4v8lqxzHi0mWQ=s240-c-k-c0x00ffffff-no-rj"));
        최겨울_P.addTags(new ProfileTag(최겨울_P, 패션));
        profileRepository.save(최겨울_P);
        Member 최겨울 = new Member("dummy7@dummy", "dummy", "최겨울", Authority.ROLE_INFLUENCER, 최겨울_P);
        memberRepository.save(최겨울);

        Profile 백종원_P = new Profile("요리 유튜버 백종원입니다.", "https://yt3.ggpht.com/J3OI66Bc7T3nheyKJKAkXR6tb-_bpCsoRMTFoslOBCXI3DpVY8eFY4LZWzww3BEgkRjMOEoI=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("백종원", "요리 유튜버 백종원입니다.", new BigInteger("6020000"), "https://yt3.ggpht.com/J3OI66Bc7T3nheyKJKAkXR6tb-_bpCsoRMTFoslOBCXI3DpVY8eFY4LZWzww3BEgkRjMOEoI=s240-c-k-c0x00ffffff-no-rj"));
        백종원_P.addTags(new ProfileTag(백종원_P, 요리));
        profileRepository.save(백종원_P);
        Member 백종원 = new Member("dummy8@dummy", "dummy", "백종원", Authority.ROLE_INFLUENCER, 백종원_P);
        memberRepository.save(백종원);

        Profile 빠니보틀_P = new Profile("여행 유튜버 빠니보틀입니다.", "https://yt3.ggpht.com/lh7GiITaDKNdh6hi2GasqJrfi6AqDaj0qqR1UvVWGZPjltJmYzuftmA65KDy_Tltu6S8k82WkQ=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("빠니보틀", "여행 유튜버 빠니보틀입니다.", new BigInteger("1970000"), "https://yt3.ggpht.com/lh7GiITaDKNdh6hi2GasqJrfi6AqDaj0qqR1UvVWGZPjltJmYzuftmA65KDy_Tltu6S8k82WkQ=s240-c-k-c0x00ffffff-no-rj"));
        빠니보틀_P.addTags(new ProfileTag(빠니보틀_P, 여행));
        profileRepository.save(빠니보틀_P);
        Member 빠니보틀 = new Member("dummy9@dummy", "dummy", "빠니보틀", Authority.ROLE_INFLUENCER, 빠니보틀_P);
        memberRepository.save(빠니보틀);

        Profile 피지컬갤러리_P = new Profile("운동 유튜버 피지컬갤러리입니다.", "https://yt3.ggpht.com/ytc/APkrFKbbUkzo5uon3hrbv76TwBgVQkZCZpFyNi-l4fyPOg=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("피지컬갤러리", "운동 유튜버 피지컬갤러리입니다.", new BigInteger("3080000"), "https://yt3.ggpht.com/ytc/APkrFKbbUkzo5uon3hrbv76TwBgVQkZCZpFyNi-l4fyPOg=s240-c-k-c0x00ffffff-no-rj"));
        피지컬갤러리_P.addTags(new ProfileTag(피지컬갤러리_P, 운동));
        profileRepository.save(피지컬갤러리_P);
        Member 피지컬갤러리 = new Member("dummy10@dummy", "dummy", "피지컬갤러리", Authority.ROLE_INFLUENCER, 피지컬갤러리_P);
        memberRepository.save(피지컬갤러리);

        Profile 행복지수103_P = new Profile("연예인 유튜버 지수입니다.", "https://yt3.ggpht.com/b53AyTQdjkbwGMJJbOoph7dXhEzavqDGykGKd_aMK1kt0Jtth15Bzx3vWPUu8gir4dU8uL1icg=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("행복지수 103%", "연예인 유튜버 지수입니다.", new BigInteger("4820000"), "https://yt3.ggpht.com/b53AyTQdjkbwGMJJbOoph7dXhEzavqDGykGKd_aMK1kt0Jtth15Bzx3vWPUu8gir4dU8uL1icg=s240-c-k-c0x00ffffff-no-rj"));
        행복지수103_P.addTags(new ProfileTag(행복지수103_P, 연예인));
        profileRepository.save(행복지수103_P);
        Member 지수 = new Member("dummy11@dummy", "dummy", "지수", Authority.ROLE_INFLUENCER, 행복지수103_P);
        memberRepository.save(지수);

        Profile 쯔양_P = new Profile("먹방 유튜버 제이플라입니다.", "https://yt3.ggpht.com/_s3C7XpwVKVr_5gDrmYJh9AOkU3wFlY9FWyZBVGVP_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("쯔양", "먹방 유튜버 쯔양입니다.", new BigInteger("8970000"), "https://yt3.ggpht.com/_s3C7XpwVKVr_5gDrmYJh9AOkU3wFlY9FWyZBVGVP_v7B09P5O4bbEZaWGpiuyT78Dk-aElE=s240-c-k-c0x00ffffff-no-rj"));
        쯔양_P.addTags(new ProfileTag(쯔양_P, 먹방));
        profileRepository.save(쯔양_P);
        Member 쯔양 = new Member("dummy12@dummy", "dummy", "쯔양", Authority.ROLE_INFLUENCER, 쯔양_P);
        memberRepository.save(쯔양);

        Profile 크림히어로즈_P = new Profile("애완/반려동물 유튜버 크림히어로즈입니다.", "https://yt3.ggpht.com/NYqzhjbY6EYcolCeLPgieC1Kj4p40ihw37blsuucCMN1M8ow6t0jg97frhDNZjesv4p6-5N8=s240-c-k-c0x00ffffff-no-rj", 0l, new YoutubeProfile("크림히어로즈", "애완/반려동물 유튜버 크림히어로즈입니다.", new BigInteger("3800000"), "https://yt3.ggpht.com/NYqzhjbY6EYcolCeLPgieC1Kj4p40ihw37blsuucCMN1M8ow6t0jg97frhDNZjesv4p6-5N8=s240-c-k-c0x00ffffff-no-rj"));
        크림히어로즈_P.addTags(new ProfileTag(크림히어로즈_P, 동물));
        profileRepository.save(크림히어로즈_P);
        Member 크림히어로즈 = new Member("dummy13@dummy", "dummy", "크림히어로즈", Authority.ROLE_INFLUENCER, 크림히어로즈_P);
        memberRepository.save(크림히어로즈);

        Profile 보람튜브_P = new Profile("키즈 유튜버 보람튜브입니다.", "https://yt3.ggpht.com/a/AATXAJwKBRNnEic2hZxIRM6E4Aaz2iF7nmdI02iMTyG2Mw=s240-c-k-c0xffffffff-no-rj-mo", 0l, new YoutubeProfile("보람튜브 브이로그", "키즈 유튜버 보람튜브입니다.", new BigInteger("26500000"), "https://yt3.ggpht.com/a/AATXAJwKBRNnEic2hZxIRM6E4Aaz2iF7nmdI02iMTyG2Mw=s240-c-k-c0xffffffff-no-rj-mo"));
        보람튜브_P.addTags(new ProfileTag(보람튜브_P, 육아));
        profileRepository.save(보람튜브_P);
        Member 보람튜브 = new Member("dummy15@dummy", "dummy", "보람튜브", Authority.ROLE_INFLUENCER, 보람튜브_P);
        memberRepository.save(보람튜브);
//
//        Profile J_Fla_P = new Profile("음악 유튜버 제이플라입니다.", "image", 0l, new YoutubeProfile("J.Fla", "음악 유튜버 제이플라입니다.", new BigInteger("17500000"), "image"));
//        J_Fla_P.addTags(new ProfileTag(J_Fla_P, 음악));
//        profileRepository.save(J_Fla_P);
//        Member J_Fla = new Member("dummy1@dummy", "dummy", "J.Fla", Authority.ROLE_INFLUENCER, J_Fla_P);
//        memberRepository.save(J_Fla);

    }
}
