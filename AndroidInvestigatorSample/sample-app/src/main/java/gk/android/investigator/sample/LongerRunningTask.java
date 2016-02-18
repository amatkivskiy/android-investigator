package gk.android.investigator.sample;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class LongerRunningTask implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Investigator.log(this);
        Investigator.stopLoggingTimes();
    }

    public static LongerRunningTask create() {
        return new LongerRunningTask();
    }
}
