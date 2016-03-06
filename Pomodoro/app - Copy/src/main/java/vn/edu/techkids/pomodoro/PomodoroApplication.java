package vn.edu.techkids.pomodoro;

import android.app.Application;

import java.util.List;

import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.entity.TaskIncome;
import vn.edu.techkids.pomodoro.entity.TaskManager;

/**
 * Created by TienVV on 12/7/15.
 */
public class PomodoroApplication extends Application {

    TaskManager mTaskManager;
    private PomodoroDbHelper mPomodoroDbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        mPomodoroDbHelper = new PomodoroDbHelper(getApplicationContext());
        mTaskManager = new TaskManager(mPomodoroDbHelper.selectAllTasks());
    }

    public PomodoroDbHelper getPomodoroDbHelper() {
        return mPomodoroDbHelper;
    }

    public TaskManager getTaskManager(){
        return mTaskManager;
    }
}
