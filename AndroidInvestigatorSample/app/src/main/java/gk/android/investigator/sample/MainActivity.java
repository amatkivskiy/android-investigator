package gk.android.investigator.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import gk.android.investigator.Investigator;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
// TODO review this
public class MainActivity extends Activity implements FirstFragment.Callback {

    private Button goToFirstFragmentButton;

    private String fruit = "cherry";
    private String nullVar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goToFirstFragmentButton = (Button) findViewById(R.id.mainActivity_goToFirstFragmentButton);
        goToFirstFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadFirstFragmentButtonClick();
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        Investigator.log(this);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Investigator.log(this, "item", item);
        if (item.getItemId() == R.id.action_settings) {
            goToSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void goToSettingsActivity() {
        Investigator.startStopWatch(this);
        Investigator.log(this);
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void onLoadFirstFragmentButtonClick() {
        Investigator.log(this);
        goToFirstFragmentButton.setVisibility(View.GONE);
        getFragmentManager().beginTransaction().add(R.id.mainActivity_root, new FirstFragment()).commit();
    }

    @Override
    public void onGoToSecondFragmentButtonClick() {
        Investigator.log(this);
        getFragmentManager().beginTransaction().replace(R.id.mainActivity_root, new SecondFragment()).commit();
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
