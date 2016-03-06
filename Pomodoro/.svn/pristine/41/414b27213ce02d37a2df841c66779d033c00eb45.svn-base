package vn.edu.techkids.pomodoro.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.entity.TimePeriod;

/**
 * Created by qhuydtvt on 12/11/2015.
 */
public class AdapterSpinnerTimePeriod extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mLayoutInflator;
    private TimePeriod[] mTimePeriods;

    private int mSelectedIndex;

    public AdapterSpinnerTimePeriod(Activity activity) {
        mActivity = activity;
        mLayoutInflator = mActivity.getLayoutInflater();
        mTimePeriods = TimePeriod.getListTimePeriod();
    }

    @Override
    public int getCount() {
        return mTimePeriods.length;
    }

    @Override
    public Object getItem(int position) {
        return mTimePeriods[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get item index
     * @param id
     * @return
     */
    public int getIndex(int id) {
        int index = 0;
        for (int i = 0; i < mTimePeriods.length; i++) {
            TimePeriod period = mTimePeriods[i];
            if (period.getId() == id) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflator.inflate(R.layout.item_list_time_period, parent, false);
            TextView tvTitle = (TextView)convertView.findViewById(R.id.item_tv_title);

            viewHolder = new ViewHolder(tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.updateView(mTimePeriods[position]);
        return convertView;
    }

    private class ViewHolder {
        private TextView tvTitle;



        public ViewHolder(TextView tvTitle) {
            this.tvTitle = tvTitle;
        }

        public void updateView(TimePeriod timePeriod) {
            tvTitle.setText(timePeriod.getTitleRes());
        }
    }
}
