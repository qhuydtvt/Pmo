package vn.edu.techkids.pomodoro.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.techkids.pomodoro.MainActivity;
import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.adapter.AdapterSpinnerTaskColor;
import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.utils.Logger;
import vn.edu.techkids.pomodoro.utils.TaskColor;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEditTaskFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private Task task;

    private TaskOperation mTaskOperation;

    private AdapterSpinnerTaskColor mAdapterSpinnerTaskColor;

    /* Views */
    private EditText mEtTaskName;
    private EditText mEtPaymnent;
    private Spinner mSpTaskColorEdit;

    public AddEditTaskFragment() {
        // Required empty public constructor
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setTaskOperation(TaskOperation taskOperation) {
        this.mTaskOperation = taskOperation;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScreenType = ScreenType.DETAIL_SCREEN;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = inflater.getContext();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        registerEvents();
    }

    private void initView() {
        View view = getView();
        mEtTaskName = (EditText) view.findViewById(R.id.et_task_name_edit);
        mEtPaymnent = (EditText) view.findViewById(R.id.et_task_payment_edit);
        mSpTaskColorEdit = (Spinner) view.findViewById(R.id.sp_task_color_edit);
        mAdapterSpinnerTaskColor = new AdapterSpinnerTaskColor((Activity) mContext);
        mSpTaskColorEdit.setAdapter(mAdapterSpinnerTaskColor);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        ((MainActivity)getActivity()).lockDrawer();

        if (mTaskOperation == TaskOperation.EDIT) {
            mEtTaskName.setText(task.getName());
            mEtPaymnent.setText(String.valueOf(task.getPayment()));
            mSpTaskColorEdit.setSelection(TaskColor.indexOf(task.getColor()));
            mToolbar.setTitle(R.string.title_screen_edit_task);
        } else  if(mTaskOperation == TaskOperation.ADD){
            mToolbar.setTitle(R.string.title_screen_add_task);
        }

        mFloatingActionButton.setImageResource(R.drawable.ic_action_save);
    }

    private void registerEvents() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void editAddTask() {
        String taskName = mEtTaskName.getText().toString().trim();
        String taskPaymentString = mEtPaymnent.getText().toString();
        String taskColor = (String) mSpTaskColorEdit.getSelectedItem();

        if (taskName.isEmpty()) {
            Toast.makeText(mContext, R.string.toast_task_empty_name, Toast.LENGTH_SHORT).show();
        }
        if (taskPaymentString.isEmpty()) {
            Toast.makeText(mContext, R.string.toast_task_empty_payment, Toast.LENGTH_SHORT).show();
        } else {
            if(mTaskOperation == TaskOperation.ADD){
                task = new Task();
            }
            task.setData(taskName, Float.parseFloat(taskPaymentString), taskColor);

            if(mTaskOperation == TaskOperation.ADD){
                mPoromodoDbHelper.insertTask(task);
                mTaskManager.add(task);
                Toast.makeText(mContext, R.string.toast_task_add_done, Toast.LENGTH_SHORT).show();
            } else if(mTaskOperation == TaskOperation.EDIT){
                mPoromodoDbHelper.updateTask(task);
                Toast.makeText(mContext, R.string.toast_task_edit_done, Toast.LENGTH_SHORT).show();
            }

            mScreenManager.back();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                editAddTask();
                break;
        }
    }
}
