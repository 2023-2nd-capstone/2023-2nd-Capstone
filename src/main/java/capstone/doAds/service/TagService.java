package capstone.doAds.service;

import capstone.doAds.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;

    public List<String> getTagNames() {
        return tagRepository.findAll().stream()
                .map(t -> t.getName())
                .collect(Collectors.toList());
    }
}
