package nanodegree.nevis.com.popularmovies.app;

import android.app.Application;
import android.content.SharedPreferences;

import nanodegree.nevis.com.popularmovies.BuildConfig;
import nanodegree.nevis.com.popularmovies.repository.OkHttpProvider;
import nanodegree.nevis.com.popularmovies.repository.RepositoryProvider;

/**
 * @author Nikita Simonov
 */

public class MovieApp extends Application {

    private static SharedPreferences sSharedPreferences;

    public static SharedPreferences getPreferences() {
        return sSharedPreferences;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Lifecycler.register(this);
        RepositoryProvider.register(OkHttpProvider.withApiKey(this, BuildConfig.API_KEY));

        sSharedPreferences = Preferences.getPrefs(this);
    }
}
