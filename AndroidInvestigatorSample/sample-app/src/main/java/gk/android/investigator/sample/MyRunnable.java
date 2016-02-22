package gk.android.investigator.sample;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class MyRunnable implements Runnable {

    @Override
    public void run() {
        Investigator.log(this);
    }

}
