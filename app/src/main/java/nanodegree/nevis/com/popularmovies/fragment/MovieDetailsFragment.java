package nanodegree.nevis.com.popularmovies.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.activity.MovieDetailsActivity;
import nanodegree.nevis.com.popularmovies.adapter.ReviewsAdapter;
import nanodegree.nevis.com.popularmovies.adapter.TrailersAdapter;
import nanodegree.nevis.com.popularmovies.adapter.viewholder.TrailerViewHolder;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.model.Review;
import nanodegree.nevis.com.popularmovies.model.Video;
import nanodegree.nevis.com.popularmovies.presenter.MovieDetailsPresenter;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.utils.ImageUtil;
import nanodegree.nevis.com.popularmovies.utils.VideoUtils;
import nanodegree.nevis.com.popularmovies.view.MovieDetailsView;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsView, TrailerViewHolder.OnClickListener {

    @BindView(R.id.tv_title)
    TextView mTitleTextView;

    @BindView(R.id.tv_overview)
    TextView mOverviewTextView;

    @BindView(R.id.tv_rating)
    TextView mRatingTextView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.iv_image)
    ImageView mImageView;

    @BindView(R.id.progress_trailers)
    ProgressBar mTrailersProgress;

    @BindView(R.id.progress_reviews)
    ProgressBar mReviewsProgress;

    @BindView(R.id.rv_trailers)
    RecyclerView mTrailersRecycler;

    @BindView(R.id.rv_reviews)
    RecyclerView mReviewsRecycler;

    private MovieDetailsPresenter mPresenter;
    private TrailersAdapter mTrailersAdapter;

    @NonNull
    private ReviewsAdapter mReviewsAdapter = new ReviewsAdapter();

    public static MovieDetailsFragment create(@NonNull Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(MovieDetailsActivity.EXTRA_MOVIE, movie);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_movie_details, container, false);
        ButterKnife.bind(this, view);

        Movie movie = getArguments().getParcelable(MovieDetailsActivity.EXTRA_MOVIE);
        initPresenter(movie);
        mTrailersAdapter = new TrailersAdapter(this);
        initTrailersRecycler();
        initReviewsRecycler();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MovieDetailsActivity) {
            ((MovieDetailsActivity) getActivity()).showTransition();
        } else {
            mToolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.onHomeButtonPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void bindToolbarTitle(@NonNull String title) {
        // TODO: 13/09/16
    }

    @Override
    public void bindImage(@NonNull Movie movie) {
        ImageUtil.loadMovie(mImageView, movie, ImageUtil.WIDTH_500);
    }

    @Override
    public void bindMovieTitle(@NonNull String title, @NonNull String date) {
        mTitleTextView.setText(getString(R.string.movie_title, title, date));
    }

    @Override
    public void bindMovieOverview(@NonNull String overview) {
        mOverviewTextView.setText(overview);
    }

    @Override
    public void bindAverageRating(@NonNull String average, @NonNull String max) {
        mRatingTextView.setText(getString(R.string.rating, average, max));
    }

    @Override
    public void closeScreen() {
        getActivity().onBackPressed();
    }

    @Override
    public void showTrailersLoadingIndicator() {
        mTrailersProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTrailersLoadingIndicator() {
        mTrailersProgress.setVisibility(View.GONE);
    }

    @Override
    public void showReviewsLoadingIndicator() {
        mReviewsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideReviewsLoadingIndicator() {
        mReviewsProgress.setVisibility(View.GONE);
    }

    @Override
    public void showTrailers(@NonNull List<Video> trailers) {
        mTrailersAdapter.setTrailers(trailers);
    }

    @Override
    public void showReviews(@NonNull List<Review> reviews) {
        mReviewsAdapter.setReviews(reviews);
    }

    @Override
    public void showNetworkError() {
        showErrorMessage(getString(R.string.error_network));
    }

    @Override
    public void showUnexpectedError() {
        showErrorMessage(getString(R.string.error_unexpected));
    }

    @Override
    public void showErrorMessage(@NonNull String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onTrailerClick(@NonNull Video video) {
        mPresenter.onTrailerClick(video);
    }

    @Override
    public void browseVideo(@NonNull String videoUrl) {
        VideoUtils.browseVideo(getActivity(), videoUrl);
    }

    private void initPresenter(Movie movie) {
        mPresenter = new MovieDetailsPresenter();
        mPresenter.init(this, movie, RxLoader.get(this));
    }

    private void initTrailersRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mTrailersRecycler.setNestedScrollingEnabled(false);
        mTrailersRecycler.setLayoutManager(layoutManager);
        mTrailersRecycler.setAdapter(mTrailersAdapter);
    }

    private void initReviewsRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mReviewsRecycler.setNestedScrollingEnabled(false);
        mReviewsRecycler.setLayoutManager(layoutManager);
        mReviewsRecycler.setAdapter(mReviewsAdapter);
    }
}
