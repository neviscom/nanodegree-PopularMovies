package nanodegree.nevis.com.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Nikita Simonov
 */

public class Movie implements Parcelable {

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

    public Movie(Parcel parcel) {
        mId = parcel.readInt();
        mPosterPath = parcel.readString();
        mOverview = parcel.readString();
        mTitle = parcel.readString();
        mReleasedDate = parcel.readString();
        mVoteAverage = parcel.readDouble();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mPosterPath);
        parcel.writeString(mOverview);
        parcel.writeString(mTitle);
        parcel.writeString(mReleasedDate);
        parcel.writeDouble(mVoteAverage);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };
}
