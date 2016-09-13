package nanodegree.nevis.com.popularmovies.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import nanodegree.nevis.com.popularmovies.model.Video;

/**
 * @author Nikita Simonov
 */

public final class VideoUtils {

    private static final String YOUTUBE = "https://www.youtube.com/watch?v=";

    private VideoUtils() {
    }

    @NonNull
    public static String getYouTubeVideoUrl(@NonNull Video video) {
        return YOUTUBE + video.getKey();
    }

    public static void browseVideo(@NonNull Context context, @NonNull String videoUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(videoUrl));
        context.startActivity(intent);
    }
}
