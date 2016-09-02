package nanodegree.nevis.com.popularmovies.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Movie;
import utils.ImageUtil;

/**
 * @author Nikita Simonov
 */

public class MovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_movie)
    ImageView mMoviePoster;

    @Nullable
    private OnItemClickListener mOnItemClickListener;

    @Nullable
    private Movie mMovie;

    public MovieViewHolder(View itemView, @Nullable final OnItemClickListener listener) {
        super(itemView);
        mOnItemClickListener = listener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Movie movie) {
        mMovie = movie;
        ImageUtil.loadMovie(mMoviePoster, movie, ImageUtil.WIDTH_185);
    }

    public void setImageSize(int width, int height) {
        ViewGroup.LayoutParams layoutParams = mMoviePoster.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        mMoviePoster.requestLayout();
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.iv_movie)
    void onClick() {
        if (mOnItemClickListener != null && mMovie != null) {
            mOnItemClickListener.onItemClick(mMovie);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull Movie item);
    }
}
