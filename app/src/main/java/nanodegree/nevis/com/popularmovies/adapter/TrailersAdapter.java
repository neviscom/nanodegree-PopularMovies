package nanodegree.nevis.com.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.adapter.viewholder.TrailerViewHolder;
import nanodegree.nevis.com.popularmovies.model.Video;

/**
 * @author Nikita Simonov
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    @NonNull
    private final List<Video> mTrailers = new ArrayList<>();

    public void setTrailers(@NonNull Collection<Video> trailers) {
        mTrailers.clear();
        mTrailers.addAll(trailers);
        notifyDataSetChanged();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bind(mTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }
}
