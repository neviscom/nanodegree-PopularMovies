package nanodegree.nevis.com.popularmovies.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.app.MovieApp;
import nanodegree.nevis.com.popularmovies.app.Preferences;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.repository.RepositoryProvider;
import nanodegree.nevis.com.popularmovies.rx.RxDecorator;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.view.MoviesView;
import nanodegree.nevis.com.popularmovies.view.StubLoadingView;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Nikita Simonov
 */

public class MoviesPresenter {

    private MoviesView mMoviesView;
    private RxLoader mRxLoader;

    public MoviesPresenter() {
    }

    public void init(@NonNull MoviesView view, @NonNull RxLoader rxLoader) {
        mMoviesView = view;
        mRxLoader = rxLoader;
    }

    public void onSortChanged(boolean isPopular) {
        if (Preferences.isPopularOrder(MovieApp.getPreferences()) == isPopular) {
            return;
        }
        Preferences.setPopularMovieOrder(MovieApp.getPreferences(), isPopular);
        loadMovies();
    }

    public void loadMovies() {
        final boolean isPopular = Preferences.isPopularOrder(MovieApp.getPreferences());
        Observable<List<Movie>> observable = isPopular
                ? RepositoryProvider.provideMovieRepository().getPopular()
                : RepositoryProvider.provideMovieRepository().getTopRated();
        observable
                .lift(mRxLoader.<List<Movie>>lifecycle())
                .compose(mRxLoader.<List<Movie>>async())
                .compose(RxDecorator.<List<Movie>>loading(new StubLoadingView()))
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        updateTitle(isPopular);
                    }
                })
                .subscribe(
                        new Action1<List<Movie>>() {
                            @Override
                            public void call(List<Movie> movies) {
                                handleResponse(movies);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                handleError(throwable);
                            }
                        }
                );
    }

    private void updateTitle(boolean isPopular) {
        mMoviesView.bindTitle(isPopular
                ? R.string.most_popular_movies_title
                : R.string.top_rated_movies_title);
    }

    private void handleResponse(@NonNull List<Movie> movies) {
        mMoviesView.showMovies(movies);
    }

    private void handleError(@NonNull Throwable throwable) {
        Log.d("Presenter", throwable.toString());
        // TODO: 29/07/16
    }
}
