package vn.edu.techkids.pomodoro.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.adapter.AdapterListTaskIncome;
import vn.edu.techkids.pomodoro.entity.TaskIncome;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTaskIncome extends BaseFragment {

    public static final String TAG_MONTHLY = "Months";
    public static final String TAG_WEEKLY = "Weeks";

    private View mView;
    private ListView ltvTaskIncome;
    protected AdapterListTaskIncome mAdapterListTaskIncome;

    public FragmentTaskIncome() {
        // Required empty public constructor
    }

    public void setAdapterListTaskIncome(AdapterListTaskIncome adapterListTaskIncome) {
        mAdapterListTaskIncome = adapterListTaskIncome;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView =  inflater.inflate(R.layout.fragment_task_income, container, false);
        initLayout();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initLayout() {
        ltvTaskIncome = (ListView)mView.findViewById(R.id.ltv_task_income);

        /*mAdapterListTaskIncome = new AdapterListTaskIncome(
                TaskIncome.getWeeklyTaskIncomeList(mPoromodoDbHelper),
                getActivity()); */
        /*initAdapter();*/

        ltvTaskIncome.setAdapter(mAdapterListTaskIncome);

        mToolbar.setTitle(R.string.title_screen_income);
        showOrHideFloatingActionButton(false);
    }

    /*protected abstract void initAdapter();*/
}
