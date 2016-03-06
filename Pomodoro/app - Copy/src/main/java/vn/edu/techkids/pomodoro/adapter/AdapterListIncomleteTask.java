package vn.edu.techkids.pomodoro.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;

import java.util.List;

import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.entity.TaskManager;
import vn.edu.techkids.pomodoro.entity.TaskStatus;

/**
 * Created by qhuydtvt on 12/25/2015.
 */
public class AdapterListIncomleteTask extends AdapterListTask {

    public AdapterListIncomleteTask(Activity activity, TaskManager taskManager, View.OnClickListener onEditMenuClickListener, CompoundButton.OnCheckedChangeListener onCompleteCheckBoxCheckedListener) {
        super(activity, taskManager, onEditMenuClickListener, onCompleteCheckBoxCheckedListener);
    }

    @Override
    protected List<Task> getVisibleTaskList() {
        return mTaskManager.getTaskList(TaskStatus.INCOMPLETE);
    }
}
