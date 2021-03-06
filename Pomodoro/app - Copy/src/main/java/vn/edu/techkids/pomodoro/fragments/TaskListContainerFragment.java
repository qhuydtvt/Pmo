package vn.edu.techkids.pomodoro.fragments;


import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.utils.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskListContainerFragment extends BaseFragment implements TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private View mView;

    public TaskListContainerFragment() {

    }

    public void select(String tabId) {
        TaskListFragment fragment = TaskListFragment.create(tabId);
        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_task_list_container, fragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_task_list_container, container, false);
        //select(TaskListFragment.TAG_INCOMPLETE);
        /*initView();*/
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
    }

    private void initView() {
        mView = getView();
        mTabHost = (TabHost)mView.findViewById(R.id.th_task_list_container);

        /* Initialize TabHost */
        mTabHost.setup();

        mTabHost.addTab(createTabSpec(TaskListFragment.TAG_INCOMPLETE, TaskListFragment.TAG_INCOMPLETE));
        mTabHost.addTab(createTabSpec(TaskListFragment.TAG_COMPLETE, TaskListFragment.TAG_COMPLETE));
        mTabHost.setOnTabChangedListener(this);
        select(TaskListFragment.TAG_INCOMPLETE);
    }

    private TabSpec createTabSpec(String tag, String label) {
        TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(label);
        tabSpec.setContent(android.R.id.tabcontent);
        return tabSpec;
    }

    @Override
    public void onTabChanged(String tabId) {
        select(tabId);
    }
}
