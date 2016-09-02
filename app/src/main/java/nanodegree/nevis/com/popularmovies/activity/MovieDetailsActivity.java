package nanodegree.nevis.com.popularmovies.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.presenter.MovieDetailsPresenter;
import nanodegree.nevis.com.popularmovies.view.MovieDetailsView;
import utils.ImageUtil;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsView {

    private static final String EXTRA_MOVIE = "movie";
    private static final String IMAGE = "image";

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


    public static void navigate(@NonNull AppCompatActivity activity, @NonNull View transitionImage,
                                @NonNull Movie movie) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                transitionImage, IMAGE);
        ActivityCompat.startActivity(activity, intent, options.toBundle());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_movie_details);
        ButterKnife.bind(this);

        ViewCompat.setTransitionName(findViewById(R.id.app_bar), IMAGE);

        initToolbar();
        initPresenter();
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

    public void prepareForAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }

    @Override
    public void bindToolbarTitle(@NonNull String title) {
        CollapsingToolbarLayout toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolbarLayout.setTitle(title);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
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
        onBackPressed();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initPresenter() {
        mPresenter = new MovieDetailsPresenter();
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        mPresenter.init(this, movie);
    }
}
