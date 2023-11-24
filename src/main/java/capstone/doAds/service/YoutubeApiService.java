package capstone.doAds.service;

import com.google.api.client.json.gson.GsonFactory;
import org.springframework.stereotype.Service;
import com.google.api.services.youtube.YouTube;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.youtube.model.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Service
public class YoutubeApiService {

    /** Global instance properties filename. */
    private static String PROPERTIES_FILENAME = "youtube.properties";

    /** Global instance of the HTTP transport. */
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = new GsonFactory();

    /** Global instance of the max number of videos we want returned (50 = upper limit per page). */
    private static final long NUMBER_OF_VIDEOS_RETURNED = 25;

    /** Global instance of Youtube object to make all API requests. */
    private static YouTube youtube;

    private static String PRIVATE_KEY = "AIzaSyBHs3bBdPSGlMUYScrYJw-fQCcTl6bQMuw";

    public List<Video> getPopularVideos() {

        try{
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {}
            }).setApplicationName("youtube-cmdline-search-sample").build();

            YouTube.Videos.List videos = youtube.videos().list("id,snippet");

            String apiKey = PRIVATE_KEY;
            videos.setKey(apiKey);
            videos.setChart("mostPopular");
            videos.setRegionCode("kr");
            videos.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            VideoListResponse videoResponse = videos.execute();
            List<Video> videoList = videoResponse.getItems();

            if(videoList != null) {
                videoPrint(videoList.iterator());
            }

            return videoList;

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public List<Channel> getChannelData(String channelId) {

        try {
            youtube = new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {}
            }).setApplicationName("youtube-cmdline-search-sample").build();

            YouTube.Channels.List channels = youtube.channels().list("id,snippet,statistics");

            String apiKey = PRIVATE_KEY;
            channels.setKey(apiKey);
            channels.setId(channelId);
            channels.setMaxResults(1l);

            ChannelListResponse channelListResponse = channels.execute();
            List<Channel> channelList = channelListResponse.getItems();

            if(channelList != null) {
                channelPrint(channelList.iterator());
            }

            return channelList;

        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    private static void videoPrint(Iterator<Video> iteratorVideoResults) {

        System.out.println("\n=============================================================");
        System.out.println();
        System.out.println("=============================================================\n");

        if (!iteratorVideoResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorVideoResults.hasNext()) {

            //Video singleVideo = iteratorSearchResults.next();
            Video singleVideo = iteratorVideoResults.next();

            System.out.println(singleVideo);
            VideoPlayer vp = singleVideo.getPlayer();

            // Double checks the kind is video.
            if (singleVideo.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = (Thumbnail) singleVideo.getSnippet().getThumbnails().get("default");

                System.out.println("\n-------------------------------------------------------------\n");
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }

    private static void channelPrint(Iterator<Channel> iteratorChannelResults) {

        System.out.println("\n=============================================================");
        System.out.println();
        System.out.println("=============================================================\n");

        if (!iteratorChannelResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorChannelResults.hasNext()) {

            //Video singleVideo = iteratorSearchResults.next();
            Channel singleChannel = iteratorChannelResults.next();

            System.out.println(singleChannel);

            // Double checks the kind is video.
            if (singleChannel.getKind().equals("youtube#channel")) {

                System.out.println("\n-------------------------------------------------------------\n");
                System.out.println(" 채널 제목: " + singleChannel.getSnippet().getTitle());
                System.out.println(" 채널 설명: " + singleChannel.getSnippet().getDescription());
                System.out.println(" 구독자 수: " + singleChannel.getStatistics().getSubscriberCount());
                System.out.println(" 공개 업로드 동영상 수: " + singleChannel.getStatistics().getVideoCount());
                System.out.println(" 채널 조회 횟수: " + singleChannel.getStatistics().getViewCount());
                System.out.println("\n-------------------------------------------------------------\n");
            }
        }
    }
}
