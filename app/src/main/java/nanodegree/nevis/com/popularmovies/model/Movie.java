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

    private boolean mIsFavourite;

    public Movie() {
    }

    public Movie(Parcel parcel) {
        mId = parcel.readInt();
        mPosterPath = parcel.readString();
        mOverview = parcel.readString();
        mTitle = parcel.readString();
        mReleasedDate = parcel.readString();
        mVoteAverage = parcel.readDouble();

        boolean[] values = new boolean[1];
        parcel.readBooleanArray(values);
        mIsFavourite = values[0];
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

    public boolean isFavourite() {
        return mIsFavourite;
    }

    public void setFavourite(boolean favourite) {
        mIsFavourite = favourite;
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
        parcel.writeBooleanArray(new boolean[]{mIsFavourite});
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return mId == movie.mId;

    }

    @Override
    public int hashCode() {
        return mId;
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
