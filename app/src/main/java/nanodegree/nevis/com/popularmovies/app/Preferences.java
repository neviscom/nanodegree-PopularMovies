package nanodegree.nevis.com.popularmovies.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * @author Nikita Simonov
 */

public final class Preferences {

    private static final String SETTINGS_NAME = "moviePrefs";

    private static final String SORT_ORDER_KEY = "movieSortOrder";

    private static final String POPULAR_MOVIE_ORDER = "popular";
    private static final String TOP_RATED_MOVIE_ORDER = "topRated";

    public static boolean isPopularOrder(@NonNull SharedPreferences prefs) {
        if (!prefs.contains(SORT_ORDER_KEY)) {
            prefs.edit().putString(SORT_ORDER_KEY, POPULAR_MOVIE_ORDER).apply();
            return true;
        }
        return TextUtils.equals(POPULAR_MOVIE_ORDER, prefs.getString(SORT_ORDER_KEY, ""));
    }

    public static void setPopularMovieOrder(@NonNull SharedPreferences prefs, boolean isPopular) {
        prefs.edit()
                .putString(SORT_ORDER_KEY, isPopular
                        ? POPULAR_MOVIE_ORDER
                        : TOP_RATED_MOVIE_ORDER)
                .apply();
    }

    @NonNull
    static SharedPreferences getPrefs(@NonNull Context context) {
        return context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }
}
