package nanodegree.nevis.com.popularmovies.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import nanodegree.nevis.com.popularmovies.rx.RxLoader;

/**
 * @author Nikita Simonov
 */

public class Lifecycler implements Application.ActivityLifecycleCallbacks {

    public static void register(@NonNull Application app) {
        app.registerActivityLifecycleCallbacks(new Lifecycler());
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        final FragmentManager fm = ((AppCompatActivity) activity).getSupportFragmentManager();
        if (fm.findFragmentByTag(RxLoader.class.getName()) == null) {
            fm.beginTransaction()
                    .add(new RxLoader(), RxLoader.class.getName())
                    .commitAllowingStateLoss();
            fm.executePendingTransactions();
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
