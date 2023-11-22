package capstone.doAds.config;

import capstone.doAds.domain.Tag;
import capstone.doAds.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        tagRepository.save(new Tag("뷰티"));
        tagRepository.save(new Tag("게임"));
        tagRepository.save(new Tag("경제"));
        tagRepository.save(new Tag("IT"));
        tagRepository.save(new Tag("패션"));
        tagRepository.save(new Tag("요리"));
        tagRepository.save(new Tag("여행"));
        tagRepository.save(new Tag("자동차"));
        tagRepository.save(new Tag("운동"));
        tagRepository.save(new Tag("건강"));
        tagRepository.save(new Tag("엔터테인먼트"));
        tagRepository.save(new Tag("음악"));
        tagRepository.save(new Tag("먹방"));
        tagRepository.save(new Tag("동물"));
        tagRepository.save(new Tag("육아"));
    }
}
