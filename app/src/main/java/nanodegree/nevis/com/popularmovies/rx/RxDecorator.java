package nanodegree.nevis.com.popularmovies.rx;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import nanodegree.nevis.com.popularmovies.view.ErrorView;
import nanodegree.nevis.com.popularmovies.view.LoadingView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Nikita Simonov
 */

public final class RxDecorator {

    private static final List<Class<? extends IOException>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class
    );

    private RxDecorator() {
        //Unnecessary constructor
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> loading(@NonNull final LoadingView view) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .doOnSubscribe(new Action0() {
                            @Override
                            public void call() {
                                view.showLoadingIndicator();
                            }
                        })
                        .doOnTerminate(new Action0() {
                            @Override
                            public void call() {
                                view.hideLoadingIndicator();
                            }
                        });
            }
        };
    }

    @NonNull
    public static Action1<Throwable> error(@NonNull final ErrorView view) {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if (throwable instanceof HttpException) {
                    String message = ((HttpException) throwable).message();
                    view.showErrorMessage(message);
                } else if (NETWORK_EXCEPTIONS.contains(throwable.getClass())) {
                    view.showNetworkError();
                } else {
                    view.showUnexpectedError();
                }
            }
        };
    }

}
