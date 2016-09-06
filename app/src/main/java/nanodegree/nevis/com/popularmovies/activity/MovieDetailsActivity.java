package nanodegree.nevis.com.popularmovies.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.View;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.fragment.MovieDetailsFragment;
import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "movie";
    private static final String IMAGE = "image";

    public static void navigate(@NonNull Activity activity, @Nullable View transitionImage,
                                @NonNull Movie movie) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);

        Bundle bundleOptions = transitionImage != null
                ? ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionImage, IMAGE)
                        .toBundle()
                : null;
        ActivityCompat.startActivity(activity, intent, bundleOptions);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareForAnimation();
        setContentView(R.layout.ac_movie_details);

        if (savedInstanceState == null) {
            Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            MovieDetailsFragment fragment = MovieDetailsFragment.create(movie);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    public void showTransition() {
        ViewCompat.setTransitionName(findViewById(R.id.app_bar), IMAGE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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

}
