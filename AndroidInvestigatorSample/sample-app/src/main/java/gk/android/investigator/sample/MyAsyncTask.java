package gk.android.investigator.sample;

import android.os.AsyncTask;

import gk.android.investigator.Investigator;

/**
 * @author Gabor_Keszthelyi
 */
public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        Investigator.log(this);
        return null;
    }

    @Override
    protected void onPreExecute() {
        Investigator.log(this);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Investigator.log(this);
    }

}
