package nanodegree.nevis.com.popularmovies.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import nanodegree.nevis.com.popularmovies.R;
import nanodegree.nevis.com.popularmovies.fragment.MoviesFragment;

public class MoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_movies);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, new MoviesFragment())
                    .commit();
        }
    }

}
