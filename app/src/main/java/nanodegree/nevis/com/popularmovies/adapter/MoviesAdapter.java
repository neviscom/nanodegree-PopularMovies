package nanodegree.nevis.com.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Movie;

/**
 * @author Nikita Simonov
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    @NonNull
    private final List<Movie> mData = new ArrayList<>();

    public MoviesAdapter() {
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
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mMoviePoster;
        private Context mContext;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            mMoviePoster = (ImageView) itemView.findViewById(R.id.iv_movie);
        }

        public void bind(Movie movie) {
            Picasso.with(mContext)
                    .load(movie.getPosterPath())
                    .into(mMoviePoster);
        }
    }
}
