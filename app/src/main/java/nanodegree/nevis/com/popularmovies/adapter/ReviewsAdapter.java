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
import nanodegree.nevis.com.popularmovies.adapter.viewholder.ReviewViewHolder;
import nanodegree.nevis.com.popularmovies.model.Review;

/**
 * @author Nikita Simonov
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    @NonNull
    private final List<Review> mReviews = new ArrayList<>();

    public void setReviews(@NonNull Collection<Review> reviews) {
        mReviews.clear();
        mReviews.addAll(reviews);
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.li_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }
}
