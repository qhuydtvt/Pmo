package vn.edu.techkids.pomodoro.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.adapter.AdapterListCompleteTask;
import vn.edu.techkids.pomodoro.adapter.AdapterListIncomleteTask;
import vn.edu.techkids.pomodoro.adapter.AdapterListTask;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.utils.Logger;

/**
 * Created by qhuydtvt on 12/18/2015.
 */
public class IncompleteTaskListFragment extends TaskListFragment {

    // Context
    private AlertDialog.Builder mBuilder;

    // Layout


    @Override
    protected void initAdapter() {
        mAdapterListTask = new AdapterListIncomleteTask(getActivity(), mTaskManager,
                this, this);
        mAdapterListTask.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Task task = (Task) mAdapterListTask.getItem(position);

        mBuilder = new AlertDialog.Builder(getActivity());
        mBuilder.setTitle(R.string.alert_title_open_timer);
        String message = getString(R.string.alert_message_open_timer, task.getName());
        mBuilder.setMessage(message);
        mBuilder.setPositiveButton(R.string.alert_open_timer_task_yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Reset timer to beginning
                mPreferenceUtils.setCurrentTime(0);
                FragmentTimer fragmentTimer = FragmentTimer.newInstance(task);
                mScreenManager.openFragment(fragmentTimer);
            }
        });
        mBuilder.setNegativeButton(R.string.alert_open_timer_no, null);
        mBuilder.create().show();
    }
}
