package vn.edu.techkids.pomodoro.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.utils.TaskColor;

/**
 * Created by qhuydtvt on 12/11/2015.
 */
public class AdapterSpinnerTaskColor extends BaseAdapter {

    private Activity mActivity;
    private LayoutInflater mLayoutInflator;

    private int mSelectedIndex;
    private TaskColor[] mTaskColors;

    public AdapterSpinnerTaskColor(Activity activity) {
        mActivity = activity;
        mLayoutInflator = mActivity.getLayoutInflater();
        mTaskColors = TaskColor.ALL_TASK_COLORS;
    }

    @Override
    public int getCount() {
        return mTaskColors.length;
    }

    @Override
    public Object getItem(int position) {
        return mTaskColors[position].getValue();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = mLayoutInflator.inflate(R.layout.item_spinner_task_color, parent, false);
            View vTaskColor = convertView.findViewById(R.id.v_task_color_edit);
            TextView tvColorName = (TextView)convertView.findViewById(R.id.tv_task_color_name_edit);
            viewHolder = new ViewHolder(vTaskColor, tvColorName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.updateView(mTaskColors[position]);
        return convertView;
    }

    private class ViewHolder {
        private View mVTaskColor;
        private TextView mTvColorName;



        public ViewHolder(View vTaskColor, TextView tvColorName) {
            this.mVTaskColor = vTaskColor;
            this.mTvColorName =tvColorName;
        }

        public void updateView(TaskColor taskColor) {
            Drawable dBackground = mVTaskColor.getBackground();
            mTvColorName.setText(taskColor.getName());
            dBackground.setColorFilter(Color.parseColor(taskColor.getValue()), PorterDuff.Mode.SRC_OVER);
        }
    }
}
