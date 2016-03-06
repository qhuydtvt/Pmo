package vn.edu.techkids.pomodoro.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;

import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.adapter.AdapterListTask;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.entity.TaskStatus;
import vn.edu.techkids.pomodoro.utils.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TaskListFragment extends BaseFragment implements OnClickListener,
        OnMenuItemClickListener, OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    public static final String TAG_INCOMPLETE = "Incomplete";
    public static final String TAG_COMPLETE = "Complete";

    private Context mContext;
    protected AdapterListTask mAdapterListTask;
    private View mView;
    private ListView mListView;

    private Task menuClickedTask;

    public TaskListFragment() {
    }

    /*====================================== FACTORIES ===========================================*/
    public static TaskListFragment create(String tag) {
        if(tag.equals(TAG_COMPLETE)) {
            return new CompleteTaskListFragment();
        }
        else if (tag.equals(TAG_INCOMPLETE)) {
            return new IncompleteTaskListFragment();
        }
        return null;
    }

    /*=================================== GETTERS & SETTERS =======================================*/
    /*public AdapterListTask.ViewMode getViewMode() {
        return mViewMode;
    }

    public void setViewMode(AdapterListTask.ViewMode mViewMode) {
        this.mViewMode = mViewMode;
    }*/

    /*=================================== HANDLERS =============================================*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_task_list, container, false);
        initView();
        registerEvents();
        return mView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_task_menu: /* Edit/Delete requested from Menu item*/
                saveMenuClickedTask(v);
                showPopupMenu(v);
                break;
            case R.id.fab: /*  Add requested from Floating Action Button */
                transitToAddEditFragment(TaskOperation.ADD);
                break;
        }
    }

    /* InComplete/Complete status requested from Menu item */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Task task = (Task) buttonView.getTag();
        if (isChecked) {
            task.setStatus(TaskStatus.COMPLETE);
        } else {
            task.setStatus(TaskStatus.INCOMPLETE);
        }

        mPoromodoDbHelper.updateTask(task);
        mAdapterListTask.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Task clickedTask = mTaskManager.get(position);
        /* Transit to Timer Screen here */
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mni_task_edit:
                Logger.d("onMenuItemClick", "Edit " + menuClickedTask.getName());
                transitToAddEditFragment(TaskOperation.EDIT);
                break;
            case R.id.mni_task_delete:
                Logger.d("onMenuItemClick", "Delete " + menuClickedTask.getName());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(R.string.alert_delete_task_confirm_msg);
                builder.setPositiveButton(R.string.alert_delete_task_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPoromodoDbHelper.deleteTask(menuClickedTask);
                        mTaskManager.remove(menuClickedTask);
                        menuClickedTask = null;
                        mAdapterListTask.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(R.string.alert_delete_task_Cancel, null);
                builder.create().show();
                break;
        }
        return false;
    }

    /*=================================== UTILITIES =============================================*/
    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(mContext, v);
        Menu menu = popupMenu.getMenu();
        popupMenu.getMenuInflater().inflate(R.menu.task_edit_menu, menu);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

    private void transitToAddEditFragment(TaskOperation taskOperation) {
        AddEditTaskFragment addEditTaskFragment = new AddEditTaskFragment();
        addEditTaskFragment.setTaskOperation(taskOperation);
        addEditTaskFragment.setTask(menuClickedTask);
        mScreenManager.openFragment(addEditTaskFragment, true);
    }

    protected void initView() {
        Logger.d("initView", "Called");
        mListView = (ListView) mView.findViewById(R.id.ltv_all_tasks);
        initAdapter();
        mListView.setAdapter(mAdapterListTask);
        mFloatingActionButton.show();
        mFloatingActionButton.setImageResource(R.drawable.ic_action_add);
        mToolbar.setTitle(R.string.title_screen_task_list);
    }

    protected abstract void initAdapter(); /*{
        *//*mAdapterListTask = new AdapterListTask(getActivity(), mTaskManager,
                mViewMode, this, this);*//*
    }*/

    private void registerEvents() {
        mListView.setOnItemClickListener(this);
        mFloatingActionButton.setOnClickListener(this);
    }

    private void saveMenuClickedTask(View v) {
        menuClickedTask = (Task) v.getTag();
    }


}
