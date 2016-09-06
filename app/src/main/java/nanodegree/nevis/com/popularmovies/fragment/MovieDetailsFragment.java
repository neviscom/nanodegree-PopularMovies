package nanodegree.nevis.com.popularmovies.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.activity.MovieDetailsActivity;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.presenter.MovieDetailsPresenter;
import nanodegree.nevis.com.popularmovies.utils.ImageUtil;
import nanodegree.nevis.com.popularmovies.view.MovieDetailsView;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsFragment extends Fragment implements MovieDetailsView {

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

    private MovieDetailsPresenter mPresenter;

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
/*        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) getActivity()
                .findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(getActivity(),
                android.R.color.transparent));*/
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

    private void initPresenter(Movie movie) {
        mPresenter = new MovieDetailsPresenter();
        mPresenter.init(this, movie);
    }
}
