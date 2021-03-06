package nanodegree.nevis.com.popularmovies.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import nanodegree.nevis.com.popularmovies.BuildConfig;
import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public final class ImageUtil {

    public static final String WIDTH_185 = "w185";
    public static final String WIDTH_342 = "w342";
    public static final String WIDTH_500 = "w500";
    public static final String WIDTH_780 = "w780";

    private ImageUtil() {
        // do nothing
    }

    public static void loadMovie(@NonNull ImageView imageView, @NonNull Movie movie, @NonNull String size) {
        String imageUrl = BuildConfig.IMAGES_BASE_URL + size + movie.getPosterPath();
        Picasso.with(imageView.getContext())
                .load(imageUrl)
                .noFade()
                .into(imageView);
    }
}
