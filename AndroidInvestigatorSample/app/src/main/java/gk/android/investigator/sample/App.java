package gk.android.investigator.sample;

import android.app.Application;

import gk.android.investigator.Investigator;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
public class App extends Application {

    static {
        Investigator.highlightInnerClasses = true;
        Investigator.methodDepth = 0;
        Investigator.tag = "Investigator";
        // ...
        // (You can of course configure them from anywhere.)
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Investigator.log(this);
    }
}
