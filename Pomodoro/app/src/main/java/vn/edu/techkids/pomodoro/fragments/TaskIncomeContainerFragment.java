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

import java.util.List;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.adapter.AdapterListTaskIncome;
import vn.edu.techkids.pomodoro.entity.TaskIncome;
import vn.edu.techkids.pomodoro.utils.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class TaskIncomeContainerFragment extends BaseFragment implements TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private View mView;

    public TaskIncomeContainerFragment() {

    }

    public void select(String tabId) {

        List<TaskIncome> taskIncomeList = TaskIncome.getTaskIncomeList(mPoromodoDbHelper, tabId);
        AdapterListTaskIncome adapterListTaskIncome = new AdapterListTaskIncome(taskIncomeList, getActivity());
        FragmentTaskIncome fragmentTaskIncome = new FragmentTaskIncome();
        fragmentTaskIncome.setAdapterListTaskIncome(adapterListTaskIncome);

        FragmentTransaction fragmentTransaction =
                getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fl_task_income_container, fragmentTaskIncome);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_task_income_container, container, false);

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
        mTabHost = (TabHost)mView.findViewById(R.id.th_task_income_container);

        /* Initialize TabHost */
        mTabHost.setup();

        mTabHost.addTab(createTabSpec(TaskIncome.TAG_MONTHLY, TaskIncome.TAG_MONTHLY));
        mTabHost.addTab(createTabSpec(TaskIncome.TAG_WEEKLY, TaskIncome.TAG_WEEKLY));

        mTabHost.setOnTabChangedListener(this);
        select(TaskIncome.TAG_MONTHLY);
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