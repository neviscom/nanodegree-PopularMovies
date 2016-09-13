package nanodegree.nevis.com.popularmovies.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

/**
 * @author Nikita Simonov
 */

public class Video {

    @SerializedName("key")
    private String mKey;

    @SerializedName("name")
    private String mName;

    @NonNull
    public String getKey() {
        return mKey;
    }

    @NonNull
    public String getName() {
        return mName;
    }
}
