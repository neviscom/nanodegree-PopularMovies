package nanodegree.nevis.com.popularmovies.model.response;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Video;

/**
 * @author Nikita Simonov
 */

public class VideosResponse {

    @SerializedName("results")
    private List<Video> mVideos;

    @NonNull
    public List<Video> getVideos() {
        return mVideos;
    }
}
