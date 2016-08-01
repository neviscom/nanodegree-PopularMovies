package nanodegree.nevis.com.popularmovies.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.GsonBuilder;

import nanodegree.nevis.com.popularmovies.BuildConfig;
import nanodegree.nevis.com.popularmovies.api.MoviesService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Nikita Simonov
 */

public class RepositoryProvider {

    private static Retrofit sRetrofit;

    @Nullable
    private static MovieRepository sMovieRepository;

    private RepositoryProvider() {
        // not implemented
    }

    public static void register(@NonNull OkHttpClient client) {
        sRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @NonNull
    public static MovieRepository provideMovieRepository() {
        if (sMovieRepository == null) {
            sMovieRepository = new MovieRepository(sRetrofit.create(MoviesService.class));
        }
        return sMovieRepository;
    }

}
