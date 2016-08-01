package nanodegree.nevis.com.popularmovies.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import nanodegree.nevis.com.popularmovies.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * @author Nikita Simonov
 */

public final class OkHttpProvider {

    private OkHttpProvider() {
    }

    public static OkHttpClient provideClient(@NonNull Context context) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    public static OkHttpClient withApiKey(@NonNull Context context, @NonNull final String apiKey) {
        return provideClient(context).newBuilder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder()
                                .addQueryParameter("api_key", apiKey)
                                .build();
                        request = request.newBuilder()
                                .url(url)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
    }
}
