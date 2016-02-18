package gk.android.investigator.sample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class MainActivity extends AppCompatActivity {

    private String fruit = "cherry";
    private String nullVar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anonymousAndInnerClassExamples();

        updateView();

        // Basic usage
        Investigator.log(this);

        // With a comment
        Investigator.log(this, "comment");

        // With variables
        Investigator.log(this, "fruit", fruit);
        Investigator.log(this, "nullVar", nullVar);
        Investigator.log(this, "fruit", fruit, "nullVar", nullVar);

        Investigator.startStopWatch(this);

        // Basic usage
        Investigator.log(this);

        // With a comment
        Investigator.log(this, "comment");

        // With variables
        Investigator.log(this, "fruit", fruit);
        Investigator.log(this, "nullVar", nullVar);
        Investigator.log(this, "fruit", fruit, "nullVar", nullVar);

        Investigator.stopLoggingTimes();


        threadExample();
    }

    private void anonymousAndInnerClassExamples() {
        new Runnable() {
            @Override
            public void run() {
                Log.d("toString", toString());
                Log.d("getClass", getClass().getName());
                Investigator.log(this, "Anonymous class");
            }
        }.run();

        new MyRunnable().run();
        new MyStaticRunnable().run();
    }

    private class MyRunnable implements Runnable {
        @Override
        public void run() {
            Log.d("toString", toString());
            Log.d("getClass", getClass().getSimpleName());
            Investigator.log(this, "Inner class (non-static)");
        }
    }

    private static class MyStaticRunnable implements Runnable {
        @Override
        public void run() {
            Log.d("toString", toString());
            Log.d("getClass", getClass().getSimpleName());
            Investigator.log(this, "Static inner class");
        }
    }


    private void updateView() {
        Investigator.log(this);
    }

    private void threadExample() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Investigator.log(this);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                storeUsername();
            }
        }, "some-background-thread").start();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Investigator.log(this);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Investigator.log(this);
            }
        }.execute();

        new MyAsyncTask().execute();
        new MyStaticAsyncTask().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Investigator.log(this);
        storeUsername();
        firstLevelMethod();
    }

    private void storeUsername() {
        new UserStore().storeUser("Jack");
    }

    private void firstLevelMethod() {
        secondLevelMethod();
    }

    private void secondLevelMethod() {
        thirdLevelMethod();
    }

    private void thirdLevelMethod() {
        fourthLevelMethod();
    }

    private void fourthLevelMethod() {
        int noOfExtraMethodDepthLogged = Investigator.methodDepth;
        Investigator.methodDepth = 10;
        Investigator.log(this);
        Investigator.methodDepth = noOfExtraMethodDepthLogged;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Investigator.log(this);
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Investigator.log(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Investigator.log(this);
        }
    }

    static class MyStaticAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Investigator.log(this);
            return null;
        }
    }
}
