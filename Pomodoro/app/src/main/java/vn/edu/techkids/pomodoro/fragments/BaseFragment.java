package vn.edu.techkids.pomodoro.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import vn.edu.techkids.pomodoro.MainActivity;
import vn.edu.techkids.pomodoro.PomodoroApplication;
import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.utils.PreferenceUtils;
import vn.edu.techkids.pomodoro.entity.TaskManager;
/**
 * Created by TienVV on 12/12/15.
 */
public class BaseFragment extends Fragment {

    public enum ScreenType {
        MAIN_SCREEN, DETAIL_SCREEN
    }

    // Context
    protected ScreenManager mScreenManager;
    protected PreferenceUtils mPreferenceUtils;
    protected FloatingActionButton mFloatingActionButton;
    protected Toolbar mToolbar;
    protected DrawerLayout mDrawerLayout;
    protected ScreenType mScreenType = ScreenType.MAIN_SCREEN;

    protected PomodoroDbHelper mPoromodoDbHelper;

    protected TaskManager mTaskManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mScreenManager = mainActivity.getScreenManager();
            mPreferenceUtils = mainActivity.getPreferenceUtils();
            mFloatingActionButton = mainActivity.getFloatingActionButton();
            mToolbar = mainActivity.getToolbar();
            mDrawerLayout = mainActivity.getDrawerLayout();
            mPoromodoDbHelper = ((PomodoroApplication)mainActivity.getApplication()).getPomodoroDbHelper();
            mTaskManager = ((PomodoroApplication)mainActivity.getApplication()).getTaskManager();
            mFloatingActionButton.setOnClickListener(null);
        } else {
            mScreenManager = ScreenManager.getInstance(getActivity());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mScreenType == ScreenType.MAIN_SCREEN) {
            ((MainActivity)getActivity()).unlockDrawer();
        }
    }

    protected void showOrHideFloatingActionButton(boolean b) {
        if(b) {
            mFloatingActionButton.show();
        }
        else {
            mFloatingActionButton.hide();
        }
    }
}
