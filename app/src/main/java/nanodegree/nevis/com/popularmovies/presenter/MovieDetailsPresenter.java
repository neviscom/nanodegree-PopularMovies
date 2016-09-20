package nanodegree.nevis.com.popularmovies.presenter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.Review;
import nanodegree.nevis.com.popularmovies.model.Video;
import nanodegree.nevis.com.popularmovies.repository.RepositoryProvider;
import nanodegree.nevis.com.popularmovies.rx.RxDecorator;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.utils.VideoUtils;
import nanodegree.nevis.com.popularmovies.view.MovieDetailsView;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsPresenter {

    private static final String MAX_RATING = "10";
    private static final String KEY_TRAILERS = "trailers";
    private static final String KEY_REVIEWS = "reviews";
    private static final String KEY_LOADED = "isLoaded";

    private static final Gson GSON = new Gson();
    private static final Type LIST_VIDEO_TYPE = new TypeToken<List<Video>>() {}.getType();
    private static final Type LIST_REVIEW_TYPE = new TypeToken<List<Review>>() {}.getType();

    private MovieDetailsView mView;
    private Movie mMovie;
    private RxLoader mRxLoader;

    @NonNull
    private List<Video> mTrailers = new ArrayList<>();

    @NonNull
    private List<Review> mReviews = new ArrayList<>();

    private boolean mIsLoaded = false;

    public MovieDetailsPresenter(@NonNull final MovieDetailsView view, @NonNull Movie movie, @NonNull RxLoader rxLoader) {
        mView = view;
        mMovie = movie;
        mRxLoader = rxLoader;
    }

    public void dispatchCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mReviews = GSON.fromJson(savedInstanceState.getString(KEY_REVIEWS), LIST_REVIEW_TYPE);
            mTrailers = GSON.fromJson(savedInstanceState.getString(KEY_TRAILERS), LIST_VIDEO_TYPE);
            mIsLoaded = savedInstanceState.getBoolean(KEY_LOADED, false);
        }
    }

    public void dispatchStart() {
        showContent();
        if (!mIsLoaded) {
            loadData();
        }
    }

    public void saveInstantState(@NonNull Bundle outState) {
        outState.putString(KEY_TRAILERS, GSON.toJson(mTrailers));
        outState.putString(KEY_REVIEWS, GSON.toJson(mReviews));
        outState.putBoolean(KEY_LOADED, mIsLoaded);
    }

    public void onHomeButtonPressed() {
        mView.closeScreen();
    }
    
    public void onTrailerClick(@NonNull Video video) {
        mView.browseVideo(VideoUtils.getYouTubeVideoUrl(video));
    }

    public void onFavouriteClick() {
        Observable<Boolean> observable;
        if (mMovie.isFavourite()) {
            observable = RepositoryProvider.provideMovieRepository().removeFromFavourite(mMovie);
        } else {
            observable = RepositoryProvider.provideMovieRepository().addToFavourite(mMovie);
        }
        observable.subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean isFavourite) {
                mMovie.setFavourite(isFavourite);
                mView.showIsFavourite(isFavourite);
            }
        });
    }

    private void loadData() {
        Observable.merge(createTrailersObservable(), createReviewsObservable())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        // do nothing
                    }
                }, RxDecorator.error(mView), new Action0() {
                    @Override
                    public void call() {
                        mIsLoaded = true;
                    }
                });
    }

    private void showContent() {
        mView.bindToolbarTitle(mMovie.getTitle());
        mView.bindImage(mMovie);
        mView.bindMovieTitle(mMovie.getTitle(), mMovie.getReleasedDate());
        mView.bindAverageRating(formatVoteAverage(mMovie.getVoteAverage()), MAX_RATING);
        mView.bindMovieOverview(mMovie.getOverview());
        mView.showIsFavourite(mMovie.isFavourite());
        if (!mTrailers.isEmpty()) {
            mView.showTrailers(mTrailers);
            mView.hideTrailersLoadingIndicator();
        }
        if (!mReviews.isEmpty()) {
            mView.showReviews(mReviews);
            mView.hideReviewsLoadingIndicator();
        }
    }

    private String formatVoteAverage(double voteAverage) {
        return new DecimalFormat("#0.0").format(voteAverage);
    }

    private Observable<List<Video>> createTrailersObservable() {
        return RepositoryProvider.provideMovieRepository()
                .getTrailers(mMovie)
                .compose(RxLoader.<List<Video>>async())
                .lift(mRxLoader.<List<Video>>lifecycle())
                .doOnNext(new Action1<List<Video>>() {
                    @Override
                    public void call(List<Video> videos) {
                        handleTrailers(videos);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showTrailersLoadingIndicator();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mView.hideTrailersLoadingIndicator();
                    }
                });
    }

    private Observable<List<Review>> createReviewsObservable() {
        return RepositoryProvider.provideMovieRepository()
                .getReviews(mMovie)
                .compose(RxLoader.<List<Review>>async())
                .lift(mRxLoader.<List<Review>>lifecycle())
                .doOnNext(new Action1<List<Review>>() {
                    @Override
                    public void call(List<Review> reviews) {
                        handleReviews(reviews);
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mView.showReviewsLoadingIndicator();
                    }
                })
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        mView.hideReviewsLoadingIndicator();
                    }
                });
    }

    private void handleTrailers(List<Video> videos) {
        mTrailers = videos;
        mView.showTrailers(videos);
    }

    private void handleReviews(List<Review> reviews) {
        mReviews = reviews;
        mView.showReviews(reviews);
    }
}
