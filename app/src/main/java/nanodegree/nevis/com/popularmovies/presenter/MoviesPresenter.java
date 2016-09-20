package nanodegree.nevis.com.popularmovies.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.app.MovieApp;
import nanodegree.nevis.com.popularmovies.app.Preferences;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.MoviesType;
import nanodegree.nevis.com.popularmovies.repository.RepositoryProvider;
import nanodegree.nevis.com.popularmovies.rx.RxDecorator;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.view.MoviesView;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Nikita Simonov
 */

public class MoviesPresenter {

    private static final String KEY_MOVIES = "movies";
    private static final String KEY_LOADED = "isLoaded";
    private static final Gson GSON = new Gson();
    private static final Type LIST_MOVIE_TYPE = new TypeToken<List<Movie>>() {}.getType();

    private MoviesView mMoviesView;
    private RxLoader mRxLoader;
    private boolean mIsLoaded = false;
    private MoviesType mMoviesType;

    @NonNull
    private List<Movie> mMovies = new ArrayList<>();

    public MoviesPresenter(@NonNull MoviesView view, @NonNull RxLoader rxLoader) {
        mMoviesView = view;
        mRxLoader = rxLoader;
    }

    public void dispatchCreate(@Nullable Bundle savedInstantState) {
        if (savedInstantState != null) {
            mMovies = GSON.fromJson(savedInstantState.getString(KEY_MOVIES), LIST_MOVIE_TYPE);
            mIsLoaded = savedInstantState.getBoolean(KEY_LOADED, false);
        }
        mMoviesType = Preferences.isPopularOrder(MovieApp.getPreferences());
    }

    public void dispatchStart() {
        showContent();
        if (!mIsLoaded || mMoviesType == MoviesType.FAVOURITE) {
            loadMovies();
        }
    }

    public void saveInstantState(@NonNull Bundle outState) {
        outState.putString(KEY_MOVIES, GSON.toJson(mMovies));
        outState.putBoolean(KEY_LOADED, mIsLoaded);
    }

    public void onSortChanged(MoviesType moviesType) {
        if (Preferences.isPopularOrder(MovieApp.getPreferences()) == moviesType) {
            return;
        }
        Preferences.setPopularMovieOrder(MovieApp.getPreferences(), moviesType);
        mMoviesType = moviesType;
        loadMovies();
    }

    private void loadMovies() {
        final MoviesType type = Preferences.isPopularOrder(MovieApp.getPreferences());

        RepositoryProvider.provideMovieRepository()
                .loadMovies(type)
                .lift(mRxLoader.<List<Movie>>lifecycle())
                .compose(RxLoader.<List<Movie>>async())
                .compose(RxDecorator.<List<Movie>>loading(mMoviesView))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        updateTitle(type);
                    }
                })
                .subscribe(
                        new Action1<List<Movie>>() {
                            @Override
                            public void call(List<Movie> movies) {
                                handleResponse(movies);
                            }
                        },
                        RxDecorator.error(mMoviesView)
                );
    }

    private void updateTitle(@NonNull MoviesType moviesType) {
        mMoviesView.bindTitle(getTitle(moviesType));
    }

    @StringRes
    private int getTitle(@NonNull MoviesType moviesType) {
        switch (moviesType) {
            case FAVOURITE:
                return R.string.favourites_movies_title;
            case TOP_RATED:
                return R.string.most_popular_movies_title;
            case POPULAR:
            default:
                return R.string.most_popular_movies_title;
        }
    }

    private void showContent() {
        if (mMovies.isEmpty()) {
            mMoviesView.showEmptyView();
        } else {
            mMoviesView.showMovies(mMovies);
            if (!mMovies.isEmpty()) {
                mMoviesView.showMovieDetails(mMovies.get(0));
            }
        }
    }

    private void handleResponse(@NonNull List<Movie> movies) {
        mMovies = movies;
        mIsLoaded = true;
        showContent();
    }
}
