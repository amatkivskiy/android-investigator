package gk.android.investigator.sample;

import gk.android.investigator.Investigator;

/**
 * No function, just to illustrate Investigator usage.
 *
 * @author Gabor_Keszthelyi
 */
public class UserStore {

    public void storeUser(String username) {
        Investigator.log(this, "username", username);

    }
}
