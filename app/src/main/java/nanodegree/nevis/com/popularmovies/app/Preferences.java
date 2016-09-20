package nanodegree.nevis.com.popularmovies.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import nanodegree.nevis.com.popularmovies.model.MoviesType;

/**
 * @author Nikita Simonov
 */

public final class Preferences {

    private static final String SETTINGS_NAME = "moviePrefs";

    private static final String SORT_ORDER_KEY = "movieSortOrder";

    public static MoviesType isPopularOrder(@NonNull SharedPreferences prefs) {
        if (!prefs.contains(SORT_ORDER_KEY)) {
            prefs.edit().putString(SORT_ORDER_KEY, MoviesType.POPULAR.name()).apply();
            return MoviesType.POPULAR;
        }

        return MoviesType.valueOf(MoviesType.class, prefs.getString(SORT_ORDER_KEY, MoviesType.POPULAR.name()));
    }

    public static void setPopularMovieOrder(@NonNull SharedPreferences prefs, MoviesType moviesType) {
        prefs.edit()
                .putString(SORT_ORDER_KEY, moviesType.name())
                .apply();
    }

    @NonNull
    static SharedPreferences getPrefs(@NonNull Context context) {
        return context.getSharedPreferences(SETTINGS_NAME, Context.MODE_PRIVATE);
    }
}
