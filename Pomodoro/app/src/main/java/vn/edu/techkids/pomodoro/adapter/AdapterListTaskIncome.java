package vn.edu.techkids.pomodoro.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.entity.MonthlyTaskIncome;
import vn.edu.techkids.pomodoro.entity.MonthlyTaskIncomeSpecific;
import vn.edu.techkids.pomodoro.entity.TaskIncome;
/*import vn.edu.techkids.pomodoro.entity.TaskIncomeHeader;*/
import vn.edu.techkids.pomodoro.entity.TaskIncomeSpecific;
import vn.edu.techkids.pomodoro.entity.WeeklyTaskIncome;
import vn.edu.techkids.pomodoro.entity.WeeklyTaskIncomeSpecific;
import vn.edu.techkids.pomodoro.utils.Logger;
import vn.edu.techkids.pomodoro.utils.TaskColor;

/**
 * Created by qhuydtvt on 12/29/2015.
 */

public class AdapterListTaskIncome extends BaseAdapter {

    private final int VIEW_TYPE_COUNT = 2;

    /* Models */
    private List<TaskIncome> mTaskIncomeList;

    /* Views */
    private Activity mActivity;
    private LayoutInflater mLayoutInflater;

    private final String[] ALL_MONTHS;

    public AdapterListTaskIncome(List<TaskIncome> taskIncomeList, Activity activity) {
        mTaskIncomeList = taskIncomeList;
        mActivity = activity;
        mLayoutInflater = activity.getLayoutInflater();
        ALL_MONTHS = mActivity.getResources().getStringArray(R.array.all_months);
    }

    @Override
    public int getCount() {
        if(mTaskIncomeList == null) {
            return 0;
        }
        else return mTaskIncomeList.size();
    }

    @Override
    public Object getItem(int position) {
        if(mTaskIncomeList == null)
            return null;
        else
            return mTaskIncomeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return ViewHolder.VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        TaskIncome taskIncome = mTaskIncomeList.get(position);
        if(taskIncome instanceof  MonthlyTaskIncomeSpecific || taskIncome instanceof  WeeklyTaskIncomeSpecific)
            return ViewHolder.VIEW_TYPE_ITEM;
        else return ViewHolder.VIEW_TYPE_HEADER;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        TaskIncome taskIncome = mTaskIncomeList.get(position);
        int type = getItemViewType(position);

        if (convertView == null)
        {
            switch (type) {
                case ViewHolder.VIEW_TYPE_HEADER:
                    convertView = mLayoutInflater.inflate(R.layout.item_list_task_income_header, parent, false);
                    break;
                case ViewHolder.VIEW_TYPE_ITEM:
                    convertView = mLayoutInflater.inflate(R.layout.item_list_task_income, parent, false);
                    break;
            }
        }

        switch (type) {
            case ViewHolder.VIEW_TYPE_HEADER:
                viewHolder = new ViewHolderHeader(convertView);
                break;
            case ViewHolder.VIEW_TYPE_ITEM:
                viewHolder = new ViewHolderItem(convertView);
                break;
        }

        viewHolder.update(taskIncome);
        return convertView;
    }

    private interface ViewHolder {
        int VIEW_TYPE_COUNT = 2;
        int VIEW_TYPE_ITEM = 0;
        int VIEW_TYPE_HEADER = 1;
        void update(TaskIncome taskIncome);
    }

    private class ViewHolderItem  implements  ViewHolder {
        private View mVColor;
        private TextView mTvName;
        private TextView mTvTotalWorkDuration;
        private TextView mTvTotalPayment;
        private TaskIncomeSpecific mTaskIncomeSpecific;

        public ViewHolderItem() {}

        public ViewHolderItem (View vItem) {
            this.mVColor = vItem.findViewById(R.id.v_task_income_color);
            this.mTvName = (TextView)vItem.findViewById(R.id.tv_task_income_name);
            this.mTvTotalWorkDuration = (TextView)vItem.findViewById(R.id.tv_task_income_total_work_duration);
            this.mTvTotalPayment = (TextView)vItem.findViewById(R.id.tv_task_income_total_payment);
        }

        public void update(TaskIncome taskIncome) {
            /*TaskColor.setBackground(mVColor, taskIncome.getColor());
            mTvName.setText(taskIncome.getName());*/

            TaskIncomeSpecific taskIncomeSpecific = (TaskIncomeSpecific)taskIncome.getExtraData();
            if(taskIncomeSpecific != null) {
                TaskColor.setBackground(mVColor, taskIncomeSpecific.getTaskColor());
                mTvName.setText(taskIncomeSpecific.getTaskName());
            }else {
                Logger.d("update", "NULL => " + taskIncome.getClass().toString());
            }
            mTvTotalWorkDuration.setText(String.valueOf(taskIncome.getTotalDuration()) + "m");
            mTvTotalPayment.setText("$" + String.valueOf(taskIncome.getTotalPayment()));
        }
    }

    private class ViewHolderHeader implements ViewHolder {

        private TextView mTvName;
        private TextView mTvTotalWorkDuration;
        private TextView mTvTotalPayment;
        private View mItemView;

        public ViewHolderHeader(View vItem) {
            this.mItemView = vItem;
            this.mTvName = (TextView)vItem.findViewById(R.id.tv_task_income_header_name);
            this.mTvTotalWorkDuration = (TextView)vItem.findViewById(R.id.tv_task_income_header_total_work_duration);
            this.mTvTotalPayment = (TextView)vItem.findViewById(R.id.tv_task_income_header_total_payment);
        }

        public void update(TaskIncome taskIncome) {
            TaskColor.setBackground(mItemView, TaskColor.VALUE_GREY);
            String title = null;
            if(taskIncome instanceof  WeeklyTaskIncome) {
                WeeklyTaskIncome weeklyTaskIncome = (WeeklyTaskIncome)taskIncome;
                title = String.valueOf(weeklyTaskIncome.getYear()) + " " + weeklyTaskIncome.getWeek();
            }else if (taskIncome instanceof MonthlyTaskIncome) {
                MonthlyTaskIncome monthlyTaskIncome = (MonthlyTaskIncome)taskIncome;
                title = String.valueOf(monthlyTaskIncome.getYear()) + " " + ALL_MONTHS[monthlyTaskIncome.getMonth()];
            }
            mTvName.setText(title);
            mTvTotalWorkDuration.setText(String.valueOf(taskIncome.getTotalDuration()) + "m");
            mTvTotalPayment.setText("$" + String.valueOf(taskIncome.getTotalPayment()));
        }
    }
}
