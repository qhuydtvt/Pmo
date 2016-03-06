package vn.edu.techkids.pomodoro.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;

import vn.edu.techkids.pomodoro.utils.Config;

/**
 * Created by qhuydtvt on 12/5/2015.
 */
public class ScreenManager {

    private static ScreenManager INSTANCE;

    // Context
    private static FragmentManager mFragmentManager;
    private static Activity mActivity;

    public static ScreenManager getInstance(Activity activity) {
        ScreenManager screenManager = new ScreenManager();
        mFragmentManager = activity.getFragmentManager();
        return screenManager;
    }

    /**
     * Open new screen
     * @param fragment
     * @param addToBackStack
     */
    public void openFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(Config.getMainLayoutResource(), fragment)
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragment.getClass().getName());
        }
        fragmentTransaction.commit();
    }

    /**
     * Open new screen and add to back stack
     * @param fragment
     */
    public void openFragment(Fragment fragment) {
        openFragment(fragment, false);
    }

    public boolean back() {
        if (mFragmentManager.getBackStackEntryCount() != 0) {
            mFragmentManager.popBackStack();
            return true;
        } else {
            return false;
        }
    }
}