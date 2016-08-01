package nanodegree.nevis.com.popularmovies.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Nikita Simonov
 */

public class Movie {

    @SerializedName("id")
    private int mId;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleasedDate;

    @SerializedName("vote_average")
    private double mVoteAverage;

    public Movie() {
    }

    public int getId() {
        return mId;
    }

    @NonNull
    public String getPosterPath() {
        return mPosterPath;
    }

    @NonNull
    public String getOverview() {
        return mOverview;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getReleasedDate() {
        return mReleasedDate;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }
}
