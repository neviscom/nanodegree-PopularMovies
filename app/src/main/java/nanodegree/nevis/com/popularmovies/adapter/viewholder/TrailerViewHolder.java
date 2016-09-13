package nanodegree.nevis.com.popularmovies.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.model.Video;

/**
 * @author Nikita Simonov
 */

public class TrailerViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_trailer_name)
    TextView mTrailerName;

    public TrailerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(@NonNull Video video) {
        mTrailerName.setText(video.getName());
    }
}
