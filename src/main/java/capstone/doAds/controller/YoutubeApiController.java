package capstone.doAds.controller;

import capstone.doAds.service.YoutubeApiService;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.Video;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class YoutubeApiController {
    private final YoutubeApiService youtubeApiService;

    @GetMapping("/popularVideos")
    public List<Video> getPopularVideos() {
        return youtubeApiService.getPopularVideos();
    }

    @GetMapping("/channelData")
    public List<Channel> getChannelData() {
        return youtubeApiService.getChannelData();
    }
}
