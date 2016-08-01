package nanodegree.nevis.com.popularmovies.app;

import android.app.Application;

import nanodegree.nevis.com.popularmovies.BuildConfig;
import nanodegree.nevis.com.popularmovies.repository.OkHttpProvider;
import nanodegree.nevis.com.popularmovies.repository.RepositoryProvider;

/**
 * @author Nikita Simonov
 */

public class MovieApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Lifecycler.register(this);
        RepositoryProvider.register(OkHttpProvider.withApiKey(this, BuildConfig.API_KEY));
    }
}
