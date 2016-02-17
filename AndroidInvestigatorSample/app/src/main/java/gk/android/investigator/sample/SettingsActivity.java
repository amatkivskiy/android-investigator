package gk.android.investigator.sample;

import android.app.Activity;
import android.os.Bundle;

import gk.android.investigator.Investigator;
import gk.android.investigator.R;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Investigator.log(this);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Investigator.log(this);
        Investigator.stopLoggingTimes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Investigator.log(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Investigator.log(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Investigator.log(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Investigator.log(this);
    }
}
