package nanodegree.nevis.com.popularmovies.presenter;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.List;

import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.Review;
import nanodegree.nevis.com.popularmovies.model.Video;
import nanodegree.nevis.com.popularmovies.repository.RepositoryProvider;
import nanodegree.nevis.com.popularmovies.rx.RxDecorator;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.view.MovieDetailsView;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsPresenter {

    private static final String MAX_RATING = "10";

    private MovieDetailsView mView;
    private Movie mMovie;
    private RxLoader mRxLoader;

    public MovieDetailsPresenter() {
        // do nothing
    }

    public void init(@NonNull final MovieDetailsView view, @NonNull Movie movie, @NonNull RxLoader rxLoader) {
        mView = view;
        mMovie = movie;
        mRxLoader = rxLoader;

        Observable.merge(createTrailersObservable(), createReviewsObservable())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object o) {
                        // do nothing
                    }
                }, RxDecorator.error(mView));

        showContent();
    }

    public void onHomeButtonPressed() {
        mView.closeScreen();
    }

    private void showContent() {
        mView.bindToolbarTitle(mMovie.getTitle());
        mView.bindImage(mMovie);
        mView.bindMovieTitle(mMovie.getTitle(), mMovie.getReleasedDate());
        mView.bindAverageRating(formatVoteAverage(mMovie.getVoteAverage()), MAX_RATING);
        mView.bindMovieOverview(mMovie.getOverview());
    }

    private String formatVoteAverage(double voteAverage) {
        return new DecimalFormat("#0.0").format(voteAverage);
    }

    private Observable<List<Video>> createTrailersObservable() {
        return RepositoryProvider.provideMovieRepository()
                .getTrailers(mMovie)
                .cache()
                .compose(mRxLoader.<List<Video>>async())
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
                .cache()
                .compose(mRxLoader.<List<Review>>async())
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
        mView.showTrailers(videos);
    }

    private void handleReviews(List<Review> reviews) {
        mView.showReviews(reviews);
    }
}
