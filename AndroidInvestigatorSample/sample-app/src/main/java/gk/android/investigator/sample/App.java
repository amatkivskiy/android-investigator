package gk.android.investigator.sample;

import android.app.Application;

import gk.android.investigator.Investigator;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Investigator.log(this);
    }
}
