package gk.android.investigator.sample;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import gk.android.investigator.Investigator;
import gk.android.investigator.R;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Investigator.log(this);
        View layout = inflater.inflate(R.layout.fragment_second, null);

        Button removeFragmentButton = (Button) layout.findViewById(R.id.secondFragment_removeThisFragmentButton);
        removeFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Investigator.log(this);
                getFragmentManager().beginTransaction().remove(SecondFragment.this).commit();
            }
        });

        Button stubButton = (Button) layout.findViewById(R.id.secondFragment_stubButton);
        stubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Investigator.log(this);
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
}
