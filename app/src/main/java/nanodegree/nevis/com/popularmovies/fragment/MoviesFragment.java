package nanodegree.nevis.com.popularmovies.fragment;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.activity.MovieDetailsActivity;
import nanodegree.nevis.com.popularmovies.adapter.MoviesAdapter;
import nanodegree.nevis.com.popularmovies.adapter.viewholder.MovieViewHolder;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.presenter.MoviesPresenter;
import nanodegree.nevis.com.popularmovies.rx.RxLoader;
import nanodegree.nevis.com.popularmovies.view.MoviesView;
import nanodegree.nevis.com.popularmovies.widget.LoadingDialog;

/**
 * @author Nikita Simonov
 */

public class MoviesFragment extends Fragment implements MoviesView, MovieViewHolder.OnItemClickListener {

    private static final double ASPECT_RATIO = 278d / 185d;

    @BindView(R.id.rv_movies)
    RecyclerView mRecyclerView;

    @BindView(R.id.tv_empty)
    View mEmptyView;

    private LoadingDialog mLoadingDialog;
    private MoviesPresenter mPresenter;
    private MoviesAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initPresenter();
        mLoadingDialog = LoadingDialog.create(R.string.loading_text);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_movies, container, false);
        ButterKnife.bind(this, view);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initRecyclerView();

        mPresenter.loadMovies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.movies_menu, menu);
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
        getActivity().setTitle(titleId);
    }

    @Override
    public void showMovies(@NonNull List<Movie> movies) {
        mAdapter.setData(movies);
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showMovieDetails(@NonNull Movie movie) {
        if (isDetailsFragmentVisible()) {
            navigateToMovieDetails(null, movie);
        }
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(@NonNull View view, @NonNull Movie item) {
        navigateToMovieDetails(view, item);
    }

    @Override
    public void showLoadingIndicator() {
        mLoadingDialog.show(getFragmentManager());
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingDialog.cancel();
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

    private void initPresenter() {
        mPresenter = new MoviesPresenter();
        mPresenter.init(this, RxLoader.get(this));
    }

    private void initRecyclerView() {
        int moviesColumnsCount = getResources().getInteger(R.integer.movies_columns_count);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(),
                moviesColumnsCount));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {
        int moviesColumnsCount = getResources().getInteger(R.integer.movies_columns_count);
        int windowColumnsCount = getResources().getInteger(R.integer.window_columns_count);
        int imageWidth = getResources().getDisplayMetrics().widthPixels / moviesColumnsCount
                / windowColumnsCount;
        mAdapter = new MoviesAdapter(this, imageWidth, (int) (imageWidth * ASPECT_RATIO));
        mAdapter.onAttachedToRecyclerView(mRecyclerView);
    }

    private void navigateToMovieDetails(@Nullable View view, @NonNull Movie movie) {
        if (isDetailsFragmentVisible()) {
            showMovieDetailsFragment(movie);
        } else {
            MovieDetailsActivity.navigate(getActivity(), view, movie);
        }
    }
    
    private void showMovieDetailsFragment(@NonNull Movie movie) {
        getActivity().getFragmentManager().beginTransaction()
                .replace(R.id.details_container, MovieDetailsFragment.create(movie))
                .commit();
    }

    private boolean isDetailsFragmentVisible() {
        boolean isTablet = getResources().getBoolean(R.bool.md_is_tablet);
        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        return isTablet && isLandscape;
    }
}
