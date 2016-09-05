package nanodegree.nevis.com.popularmovies.view;

import android.support.annotation.NonNull;

/**
 * @author Nikita Simonov
 */

public interface ErrorView {

    void showNetworkError();

    void showUnexpectedError();

    void showErrorMessage(@NonNull String message);

}
