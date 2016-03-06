package vn.edu.techkids.pomodoro.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;
import vn.edu.techkids.pomodoro.entity.TaskRecord;
import vn.edu.techkids.pomodoro.utils.Config;
import vn.edu.techkids.pomodoro.utils.Constants;
import vn.edu.techkids.pomodoro.utils.IntentUtils;
import vn.edu.techkids.pomodoro.utils.Logger;
import vn.edu.techkids.pomodoro.utils.PreferenceUtils;
import vn.edu.techkids.pomodoro.utils.Utils;

/**
 * Created by TienVV on 12/12/15.
 */
public class TimerService extends Service {

    public enum TimerType {
        IN_PROGRESS, BREAKING
    }

    // Intent constants
    public static final String INTENT_ACTION_START = "intent_action_start";
    public static final String INTENT_ACTION_STOP = "intent_action_stop";

    public static final String EXTRA_MAX_TIME = "extra_max_time";
    public static final String EXTRA_CURRENT_TIME = "extra_current_time";
    public static final String EXTRA_TIMER_TYPE = "extra_timer_type";
    public static final String EXTRA_TASK_ID = "extra_task_id";
    // Constants
    private static final int DELAY_TIME = 1000; // 1 second
    // Binder
    private IBinder mBinder = new LocalBinder();

    // Context
    private PreferenceUtils mPreferenceUtils;
    private PomodoroDbHelper mPomodoroDbHelper;
    private Intent mIntentBroadcastTimer;
    private long mTaskId = -1;
    private int maxTime;
    private int currentTime;
    private boolean isRunning;
    private TimerType mTimerType;
    private int currentPeriod;

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {

        @Override
        public void run() {
            if (isRunning) {
                currentTime++;
                if (currentTime >= maxTime) {
                    currentTime = 0;
                    if (mTimerType == TimerType.BREAKING) {
                        mTimerType = TimerType.IN_PROGRESS;
                        maxTime = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME,
                                Constants.SETTING_DEFAULT_WORKING_TIME);
                    } else {
                        mTimerType = TimerType.BREAKING;
                        // Increase period number when finished IN_PROGRESS
                        currentPeriod++;
                        mPreferenceUtils.setCurrentPeriod(currentPeriod);
                        maxTime = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_SHORT_BREAK,
                                    Constants.SETTING_DEFAULT_SHORT_BREAK_TIME);
                        // Update database
                        TaskRecord taskRecord = new TaskRecord(mTaskId, Utils.getTodayString(),
                                    mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME,
                                            Constants.SETTING_DEFAULT_WORKING_TIME));
                        mPomodoroDbHelper.insertTaskRecord(taskRecord);
                    }
                    mPreferenceUtils.setCurrentTimerType(mTimerType.name());
                    mIntentBroadcastTimer.putExtra(IntentUtils.INTENT_BOARDCAST_KEY_MAX_TIME, maxTime);
                }
                mPreferenceUtils.setCurrentTime(currentTime);
                mIntentBroadcastTimer.putExtra(IntentUtils.INTENT_BOARDCAST_KEY_CURRENT_TIME, currentTime);
                mIntentBroadcastTimer.putExtra(IntentUtils.INTENT_BOARDCAST_KEY_CURRENT_PRERIOD, currentPeriod);
                mIntentBroadcastTimer.putExtra(IntentUtils.INTENT_BROADCAST_TIME_TYPE, mTimerType.name());
                sendBroadcast(mIntentBroadcastTimer);
                mHandler.postDelayed(this, DELAY_TIME);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d("onCreate");
        mPreferenceUtils = new PreferenceUtils(getApplicationContext());
        mPomodoroDbHelper = new PomodoroDbHelper(getApplicationContext());
        mIntentBroadcastTimer = IntentUtils.createIntentTimerInfo();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Logger.d("onUnbind");
        mBinder = null;
        return super.onUnbind(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.i("onBind");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            this.stopSelf();
        }
        String action = intent.getAction();
        Logger.i("onStartCommand", "Action = " + action);
        if (INTENT_ACTION_START.equals(action)) {
            handleActionStart(intent);
        } else if (INTENT_ACTION_STOP.equals(action)) {
            handleActionStop();
        }
        return START_STICKY;
    }

    /**
     * Handle action start timer
     * @param intent
     */
    private void handleActionStart(Intent intent) {
        if (intent.hasExtra(EXTRA_MAX_TIME)) {
            maxTime = intent.getIntExtra(EXTRA_MAX_TIME, 0);
        } else {
            maxTime = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME,
                    Constants.SETTING_DEFAULT_WORKING_TIME);
        }
        if (intent.hasExtra(EXTRA_CURRENT_TIME)) {
            currentTime = intent.getIntExtra(EXTRA_CURRENT_TIME, 0);
        } else {
            currentTime = mPreferenceUtils.getcurrentTime();
        }
        if (intent.hasExtra(EXTRA_TASK_ID)) {
            mTaskId = intent.getLongExtra(EXTRA_TASK_ID, -1);
        } else {
            mTaskId = mPreferenceUtils.getCurrentTaskId();
        }
        String type = null;
        if (intent.hasExtra(EXTRA_TIMER_TYPE)) {
            type = intent.getStringExtra(EXTRA_TIMER_TYPE);
        } else {
            type = mPreferenceUtils.getCurrentTimerType();
        }
        if (type != null && type.equals(TimerType.IN_PROGRESS.name())) {
            mTimerType = TimerType.IN_PROGRESS;
        } else {
            mTimerType = TimerType.BREAKING;
        }
        Logger.d("handleActionStart", "maxTime = " + maxTime + ", currentTime = " + currentTime + ", timerType = " + mTimerType.name() +
                    ", taskId = " + mTaskId);
        if (currentTime < maxTime) {
            mPreferenceUtils.setCurrentTime(currentTime);
            mIntentBroadcastTimer.putExtra(IntentUtils.INTENT_BOARDCAST_KEY_MAX_TIME, maxTime);
            mHandler.removeCallbacks(mRunnable);
            mHandler.postDelayed(mRunnable, DELAY_TIME);
            isRunning = true;
        } else {
            Logger.e("handleActionStart", "Current time must be less than max time");
        }
    }

    /**
     * Handle action stop timer
     */
    private void handleActionStop() {
        isRunning = false;
        mPreferenceUtils.setCurrentTime(currentTime);
        mHandler.removeCallbacks(mRunnable);
        Logger.d("handleActionStop", "stopSelf");
        this.stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy");
        isRunning = false;
        mPreferenceUtils.setCurrentTime(currentTime);
        mHandler.removeCallbacks(mRunnable);
    }

    private class LocalBinder extends Binder {

        public TimerService getService() {
            return TimerService.this;
        }
    }
}