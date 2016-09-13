package nanodegree.nevis.com.popularmovies.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Video;

/**
 * @author Nikita Simonov
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_trailer_name)
    TextView mTrailerName;

    @Nullable
    private OnClickListener mOnClickListener;

    private Video mVideo;

    public TrailerViewHolder(@NonNull View itemView, @Nullable OnClickListener onClickListener) {
        super(itemView);
        mOnClickListener = onClickListener;
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Video video) {
        mVideo = video;
        mTrailerName.setText(video.getName());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.trailer_item)
    public void onTrailerClick() {
        if (mOnClickListener != null) {
            mOnClickListener.onTrailerClick(mVideo);
        }
    }

    public interface OnClickListener {
        void onTrailerClick(@NonNull Video video);
    }
}
