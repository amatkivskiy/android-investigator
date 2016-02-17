package gk.android.investigator.sample;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gk.android.investigator.Investigator;
import gk.android.investigator.R;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
public class FirstFragment extends Fragment {

    private Callback callback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Investigator.log(this);
        if (activity instanceof Callback) {
            callback = (Callback) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Investigator.log(this);
        View layout = inflater.inflate(R.layout.fragment_first, null);
        View button = layout.findViewById(R.id.firstFragment_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Investigator.log(this);
                if (callback != null) {
                    callback.onGoToSecondFragmentButtonClick();
                }
            }
        });
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Investigator.log(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Investigator.log(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Investigator.log(this);
    }

    interface Callback {

        void onGoToSecondFragmentButtonClick();
    }
}
