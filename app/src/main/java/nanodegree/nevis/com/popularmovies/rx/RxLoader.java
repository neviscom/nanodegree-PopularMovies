package nanodegree.nevis.com.popularmovies.rx;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.LongSparseArray;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Nikita Simonov
 */

public class RxLoader extends Fragment {

    private final LongSparseArray<Observable<?>> mObservables = new LongSparseArray<>();

    private CompositeSubscription mSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onStop() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
            mSubscription.clear();
            mSubscription = null;
        }
        super.onStop();
    }

    @NonNull
    public static RxLoader get(@NonNull AppCompatActivity activity) {
        return get(activity.getFragmentManager());
    }

    @NonNull
    public static RxLoader get(@NonNull Fragment fragment) {
        return get(fragment.getFragmentManager());
    }

    @NonNull
    public static RxLoader get(@NonNull FragmentManager fm) {
        final RxLoader loader = (RxLoader) fm.findFragmentByTag(RxLoader.class.getName());
        if (loader == null) {
            throw new NullPointerException("RxLoader not attached to FragmentManager");
        }
        return loader;
    }

    @NonNull
    public <T> Observable.Operator<T, T> lifecycle() {
        return new Observable.Operator<T, T>() {
            @Override
            public Subscriber<? super T> call(Subscriber<? super T> subscriber) {
                if (mSubscription == null) {
                    mSubscription = new CompositeSubscription();
                }
                mSubscription.add(subscriber);
                return subscriber;
            }
        };
    }

    @NonNull
    public static <T> Observable.Transformer<T, T> async() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public <T> Observable.Transformer<T, T> cache(final long loaderId) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                Observable<T> cachedObservable = (Observable<T>) mObservables.get(loaderId);
                if (cachedObservable == null) {
                    synchronized (mObservables) {
                        cachedObservable = (Observable<T>) mObservables.get(loaderId);
                        if (cachedObservable == null) {
                            cachedObservable = addObservable(loaderId, observable);
                        }
                    }
                }
                return cachedObservable;
            }
        };
    }

    @NonNull
    private <T> Observable<T> addObservable(long loaderId, @NonNull Observable<T> observable) {
        final Observable<T> cachedObservable = observable.cache();
        mObservables.put(loaderId, cachedObservable);
        return cachedObservable;
    }
}
