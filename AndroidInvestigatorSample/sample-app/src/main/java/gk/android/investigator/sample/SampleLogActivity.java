package gk.android.investigator.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class SampleLogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Investigator.log(this);
        Investigator.log(this, "some comment");
    }

    @Override
    protected void onStart() {
        super.onStart();
        String name = "John";
        BigDecimal pi = new BigDecimal("3.14");
        List<String> days = Arrays.asList("Mon", "Tue", "Wed");

        Investigator.log(this, "name", name);
        Investigator.log(this, "name", name, "pi", pi, "days", days);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyAsyncTask().execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Investigator.startStopWatch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Investigator.log(this);
        Investigator.stopLoggingTimes();
    }
}
