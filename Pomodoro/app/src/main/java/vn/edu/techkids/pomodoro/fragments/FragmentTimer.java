package vn.edu.techkids.pomodoro.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;

import vn.edu.techkids.pomodoro.MainActivity;
import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.service.TimerService;
import vn.edu.techkids.pomodoro.utils.Config;
import vn.edu.techkids.pomodoro.utils.Constants;
import vn.edu.techkids.pomodoro.utils.IntentUtils;
import vn.edu.techkids.pomodoro.utils.Logger;
import vn.edu.techkids.pomodoro.utils.PreferenceUtils;
import vn.edu.techkids.pomodoro.view.ProgressView;
import vn.edu.techkids.pomodoro.view.TimerView;

/**
 * Created by TienVV
 */
public class FragmentTimer extends BaseFragment implements TimerView.OnTimerListener, View.OnClickListener {

    public static FragmentTimer newInstance(Task task) {
        FragmentTimer fragment = new FragmentTimer();
        fragment.mTask = task;
        fragment.mTaskId = task.getId();
        return fragment;
    }

    // Context
    private Context mContext;
    private long mTaskId;
    private Task mTask;
    private TimerReceiver mTimerReceiver = new TimerReceiver();
    private IntentFilter mTimerFilter;
    private int mCurrentTime;
    private String mCurrentTimerType;
    private int mCurrentPeriods;
    // Layout
    private View mView;
    private TextView mTvStatusTimer;
    private TimerView mTimerView;
    private ProgressView mProgressView;
    private TextView mTvPeriodsCounter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
        mTimerFilter = new IntentFilter();
        mTimerFilter.addAction(IntentUtils.INTENT_BROADCAST_TIME_ACTION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_timer, container, false);
        // Initiate layout
        initLayout();
        // Initiate state
        initState();
        // Register receiver
        mContext.registerReceiver(mTimerReceiver, mTimerFilter);
        return mView;
    }

    /**
     * Initiate layout
     */
    private void initLayout() {
        mTvStatusTimer = (TextView) mView.findViewById(R.id.tv_status_timer);
        mTvStatusTimer.setTextColor(getResources().getColor(R.color.text_progress));
        mTvStatusTimer.setText(R.string.timer_progress);

        mTimerView = (TimerView) mView.findViewById(R.id.timer_view);
        mTimerView.setOnTimerListener(this);
        mTimerView.setOnClickListener(this);

        mProgressView = (ProgressView) mView.findViewById(R.id.progress_view);

        mTvPeriodsCounter = (TextView) mView.findViewById(R.id.tv_periods_counter);

        // Hide float action button
        mFloatingActionButton.hide();
        // Initiate title
        if (mTask != null && mTask.getName() != null) {
            mToolbar.setTitle(mTask.getName());
            mPreferenceUtils.setCurrentTaskName(mTask.getName());
        } else {
            String taskName = mPreferenceUtils.getCurrentTaskName();
            if (taskName != null && taskName.isEmpty() == false) {
                mToolbar.setTitle(taskName);
            } else {
                mToolbar.setTitle(R.string.title_screen_timer);
            }
        }
    }

    /**
     * Initiate state of all views
     */
    private void initState() {
        // Get timer type and set max time of timer
        mCurrentTimerType = mPreferenceUtils.getCurrentTimerType();
        if (TimerService.TimerType.IN_PROGRESS.name().equals(mCurrentTimerType)) {
            mTimerView.setTotalTime(mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME,
                    Constants.SETTING_DEFAULT_WORKING_TIME));
            mTvStatusTimer.setTextColor(getResources().getColor(R.color.text_progress));
            formatTimerView(TimerService.TimerType.IN_PROGRESS);
        } else if (TimerService.TimerType.BREAKING.name().equals(mCurrentTimerType)) {
            mTimerView.setTotalTime(mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_SHORT_BREAK,
                    Constants.SETTING_DEFAULT_SHORT_BREAK_TIME));
            mTvStatusTimer.setTextColor(getResources().getColor(R.color.text_breaking_time));
            formatTimerView(TimerService.TimerType.BREAKING);
        } else {
            mTimerView.setTotalTime(mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_SHORT_BREAK,
                    Constants.SETTING_DEFAULT_SHORT_BREAK_TIME));
            mTvStatusTimer.setTextColor(getResources().getColor(R.color.text_progress));
            formatTimerView(TimerService.TimerType.IN_PROGRESS);
        }
        // Progress view
        mCurrentPeriods = mPreferenceUtils.getCurrentPeriod();
        mProgressView.setCurrentProgress(mCurrentPeriods);
        mProgressView.setMaxProgress(Config.PERIOD_TARGET);
        // Text periods counter
        mTvPeriodsCounter.setText(String.format(getString(R.string.periods), mCurrentPeriods, Config.PERIOD_TARGET));
        // Timer view
        mCurrentTime = mPreferenceUtils.getcurrentTime();
        mTimerView.setCurrentTime(mCurrentTime);
    }

    /**
     * Set state of view timer
     * @param isRunning
     */
    private void setViewTimer(boolean isRunning) {
        if (isRunning) {
            mTvStatusTimer.setText(R.string.timer_progress);
            mTvStatusTimer.setTextColor(getResources().getColor(R.color.text_progress));
        } else {
            mTvStatusTimer.setText(R.string.timer_paused);
            mTvStatusTimer.setTextColor(getResources().getColor(android.R.color.white));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister receiver
        mContext.unregisterReceiver(mTimerReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.timer_view:
                if (mTimerView.isRunning()) {
                    mTimerView.setRunning(false);
                    sendTimerService(false);
                    setViewTimer(false);
                } else {
                    mTimerView.setRunning(true);
                    sendTimerService(true);
                    setViewTimer(true);
                }
                break;
        }
    }

    /**
     * Send information to timer service
     * @param start
     */
    private void sendTimerService(boolean start) {
        Intent intent = new Intent(mContext, TimerService.class);
        if (start) {
            intent.putExtra(TimerService.EXTRA_TIMER_TYPE, mCurrentTimerType);
            intent.putExtra(TimerService.EXTRA_MAX_TIME, mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME,
                    Constants.SETTING_DEFAULT_WORKING_TIME));
            intent.putExtra(TimerService.EXTRA_CURRENT_TIME, mCurrentTime);
            intent.putExtra(TimerService.EXTRA_TASK_ID, mTaskId);
            intent.setAction(TimerService.INTENT_ACTION_START);
        } else {
            intent.setAction(TimerService.INTENT_ACTION_STOP);
        }
        mContext.startService(intent);
    }

    /**
     * Handle timer info which sent from broadcast
     * @param intent
     */
    private void handleTimerInfo(Intent intent) {
        mCurrentTime = intent.getIntExtra(IntentUtils.INTENT_BOARDCAST_KEY_CURRENT_TIME, 0);
        mCurrentPeriods = intent.getIntExtra(IntentUtils.INTENT_BOARDCAST_KEY_CURRENT_PRERIOD, 0);
        mCurrentTimerType = intent.getStringExtra(IntentUtils.INTENT_BROADCAST_TIME_TYPE);
        Logger.d("handleTimerInfo", "currentTime = " + mCurrentTime + ", currentPeriod = " + mCurrentPeriods);
        mTimerView.setCurrentTime(mCurrentTime);
        mTvPeriodsCounter.setText(String.format(getString(R.string.periods), mCurrentPeriods, Config.PERIOD_TARGET));
        mProgressView.setCurrentProgress(mCurrentPeriods);
        if (TimerService.TimerType.IN_PROGRESS.name().equals(mCurrentTimerType)) {
            mTvStatusTimer.setText(R.string.timer_progress);
            mTvStatusTimer.setTextColor(getResources().getColor(R.color.text_progress));
            formatTimerView(TimerService.TimerType.IN_PROGRESS);
        } else if (TimerService.TimerType.BREAKING.name().equals(mCurrentTimerType)) {
            mTvStatusTimer.setText(R.string.timer_breaking_time);
            mTvStatusTimer.setTextColor(getResources().getColor(android.R.color.white));
            formatTimerView(TimerService.TimerType.BREAKING);
        }
        if (intent.hasExtra(IntentUtils.INTENT_BOARDCAST_KEY_MAX_TIME)) {
            int maxTime = intent.getIntExtra(IntentUtils.INTENT_BOARDCAST_KEY_MAX_TIME, 0);
            mTimerView.setTotalTime(maxTime);
        }
    }

    /**
     * Format style of TimerView
     * @param type
     */
    private void formatTimerView(TimerService.TimerType type) {
        if (TimerService.TimerType.IN_PROGRESS == type) {
            mTimerView.setColorMainCircle(getResources().getColor(R.color.timer_main_circle_inprogress));
            mTimerView.setColorBorderCircle(getResources().getColor(R.color.timer_border_circle_inprogress));
            mTimerView.setColorText(getResources().getColor(android.R.color.white));
        } else if (TimerService.TimerType.BREAKING == type) {
            mTimerView.setColorMainCircle(getResources().getColor(R.color.timer_main_circle_breaking_time));
            mTimerView.setColorBorderCircle(getResources().getColor(R.color.timer_border_circle_breaking_time));
            mTimerView.setColorText(getResources().getColor(android.R.color.white));
        }
    }

    /**
     * Receiver handle timer info from broadcast
     */
    private class TimerReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Logger.d("onReceive", "Action = " + action);
            if (IntentUtils.INTENT_BROADCAST_TIME_ACTION.equals(action)) {
                handleTimerInfo(intent);
            }
        }
    }

    @Override
    public void onTimerCreated() {
        Logger.i("onTimerCreated", null);
    }

    @Override
    public void onTimerStarted() {
        Logger.i("onTimerStarted", null);
    }

    @Override
    public void onTimerPaused() {
        Logger.i("onTimerPaused", null);
    }

    @Override
    public void onTimerResume() {
        Logger.i("onTimerResume", null);
    }

    @Override
    public void onTimerFinished() {
        Logger.i("onTimerFinished", null);
    }
}