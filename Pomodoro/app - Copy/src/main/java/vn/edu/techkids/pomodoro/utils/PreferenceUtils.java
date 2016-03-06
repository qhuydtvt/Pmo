package vn.edu.techkids.pomodoro.utils;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.techkids.pomodoro.service.TimerService;

/**
 * Created by TienVV on 12/12/15.
 */
public class PreferenceUtils {

    // Constant
    private static final String PREF_NAME = "pomodoro_pref";
    public static final String KEY_CURRENT_TASK_NAME = "key_current_task_name";
    public static final String KEY_CURRENT_TIME = "key_current_time";
    public static final String KEY_CURRENT_PERIODS = "key_current_periods";
    public static final String KEY_CURRENT_TIMER_TYPE = "key_current_timer_type";
    public static final String KEY_TASK_ID = "key_task_id";

    // Key Setting
    public static final String KEY_SETTING_WORKING_TIME = "key_setting_working_time";
    public static final String KEY_SETTING_SHORT_BREAK = "key_setting_short_break";
    public static final String KEY_SETTING_LONG_BREAK = "key_setting_long_break";
    public static final String KEY_SETTING_LONG_BREAK_AFTER = "key_setting_long_break_after";

    // Context
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferenceUtils() {
        // Prevent create instance without context
    }

    public PreferenceUtils(Context context) {
        mSharedPreferences = (SharedPreferences) context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    /**
     * Set current task name
     * @param taskName
     */
    public void setCurrentTaskName(String taskName) {
        Logger.d("setCurrentTaskName", "taskName = " + taskName);
        mEditor.putString(KEY_CURRENT_TASK_NAME, taskName);
        mEditor.commit();
    }

    /**
     * Get current task name
     * @return
     */
    public String getCurrentTaskName() {
        String result = mSharedPreferences.getString(KEY_CURRENT_TASK_NAME, null);
        Logger.d("getCurrentTaskName", "result = " + result);
        return result;
    }

    /**
     * Set current time
     * @param time
     */
    public void setCurrentTime(int time) {
        Logger.d("setCurrentTime", "time = " + time);
        mEditor.putInt(KEY_CURRENT_TIME, time);
        mEditor.commit();
    }

    /**
     * Get current time
     * @return
     */
    public int getcurrentTime() {
        int result = mSharedPreferences.getInt(KEY_CURRENT_TIME, 0);
        Logger.d("getcurrentTime", "result = " + result);
        return result;
    }

    /**
     * Set current periods
     * @param period
     */
    public void setCurrentPeriod(int period) {
        Logger.d("setCurrentPeriod", "period = " + period);
        mEditor.putInt(KEY_CURRENT_PERIODS, period);
        mEditor.commit();
    }

    /**
     * Get current periods
     * @return
     */
    public int getCurrentPeriod() {
        int result = mSharedPreferences.getInt(KEY_CURRENT_PERIODS, 0);
        Logger.d("getCurrentPeriod", "result = " + result);
        return result;
    }

    /**
     * Set current timer type
     * @param timerType
     */
    public void setCurrentTimerType(String timerType) {
        Logger.d("setCurrentTimerType", "timerType = " + timerType);
        mEditor.putString(KEY_CURRENT_TIMER_TYPE, timerType);
        mEditor.commit();
    }

    /**
     * Get current timer type
     * @return
     */
    public String getCurrentTimerType() {
        String result = mSharedPreferences.getString(KEY_CURRENT_TIMER_TYPE, TimerService.TimerType.IN_PROGRESS.name());
        Logger.d("getCurrentTimerType", "result = " + result);
        return result;
    }

    /**
     * Set current task id
     * @param taskId
     */
    public void setCurrentTaskId(int taskId) {
        Logger.d("setCurrentTaskId", "taskId = " + taskId);
        mEditor.putInt(KEY_TASK_ID, taskId);
        mEditor.commit();
    }

    /**
     * Get current task id
     * @return
     */
    public int getCurrentTaskId() {
        int result = mSharedPreferences.getInt(KEY_TASK_ID, -1);
        Logger.d("getCurrentTaskId", "result = " + result);
        return result;
    }

    /**
     * Set setting to Preference
     * @param key
     * @param value
     */
    public void setSetting(String key, int value) {
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * Get setting from Preference
     * @param key
     * @param defaultValue
     * @return
     */
    public int getSetting(String key, int defaultValue) {
        return mSharedPreferences.getInt(key, defaultValue);
    }
}