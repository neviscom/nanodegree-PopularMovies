package nanodegree.nevis.com.popularmovies.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Review;

/**
 * @author Nikita Simonov
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_author)
    TextView mAuthor;

    @BindView(R.id.tv_content)
    TextView mContent;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Review review) {
        mAuthor.setText(review.getAuthor());
        mContent.setText(review.getContent());
    }
}
