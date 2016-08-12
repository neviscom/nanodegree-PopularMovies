package nanodegree.nevis.com.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.adapter.MoviesAdapter;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.presenter.MoviesPresenter;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.view.MoviesView;

public class MoviesActivity extends AppCompatActivity implements MoviesView {

    private MoviesPresenter mPresenter;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_movies);

        mPresenter = new MoviesPresenter();
        mPresenter.init(this, RxLoader.get(this));

        mPresenter.loadPopularMovies();
    }

    @Override
    public void showMovies(List<Movie> movies) {
        // TODO: 01/08/16
    }
}
