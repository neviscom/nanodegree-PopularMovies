package nanodegree.nevis.com.popularmovies.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.adapter.MoviesAdapter;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.presenter.MoviesPresenter;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.view.MoviesView;
import nanodegree.nevis.com.popularmovies.viewholder.MovieViewHolder;

public class MoviesActivity extends AppCompatActivity implements MoviesView, MovieViewHolder.OnItemClickListener {

    private static final int COLUMN_COUNT = 2;
    private static final double ASPECT_RATIO = 278d / 185d;

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    private MoviesPresenter mPresenter;
    private MoviesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_movies);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initPresenter();
        initAdapter();
        initRecyclerView();

        mPresenter.loadMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movies_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.most_popular) {
            mPresenter.onSortChanged(true);
            return true;
        } else if (item.getItemId() == R.id.top_rated) {
            mPresenter.onSortChanged(false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void bindTitle(@StringRes int titleId) {
        setTitle(titleId);
    }

    @Override
    public void showMovies(@NonNull List<Movie> movies) {
        mAdapter.setData(movies);
    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull Movie item) {
        MovieDetailsActivity.navigate(this, view, item);
    }

    private void initPresenter() {
        mPresenter = new MoviesPresenter();
        mPresenter.init(this, RxLoader.get(this));
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), COLUMN_COUNT));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {
        int imageWidth = getResources().getDisplayMetrics().widthPixels / COLUMN_COUNT;
        mAdapter = new MoviesAdapter(this, imageWidth, (int) (imageWidth * ASPECT_RATIO));
        mAdapter.onAttachedToRecyclerView(mRecyclerView);
    }
}
