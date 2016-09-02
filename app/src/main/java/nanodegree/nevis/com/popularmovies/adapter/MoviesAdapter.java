package nanodegree.nevis.com.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Movie;
import nanodegree.nevis.com.popularmovies.viewholder.MovieViewHolder;

/**
 * @author Nikita Simonov
 */

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private final int mItemWidth;
    private final int mItemHeight;

    @NonNull
    private final List<Movie> mData = new ArrayList<>();

    @Nullable
    private MovieViewHolder.OnItemClickListener mOnItemClickListener;

    public MoviesAdapter(@Nullable MovieViewHolder.OnItemClickListener itemClickListener,
                         int itemWidth, int itemHeight) {
        mOnItemClickListener = itemClickListener;
        mItemWidth = itemWidth;
        mItemHeight = itemHeight;
    }

    public void setData(@NonNull List<Movie> movies) {
        mData.clear();
        mData.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.li_movie, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view, mOnItemClickListener);
        movieViewHolder.setImageSize(mItemWidth, mItemHeight);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
