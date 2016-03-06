package vn.edu.techkids.pomodoro.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.entity.MenuItem;

/**
 * Created by TienVV on 12/7/15.
 */
public class AdapterListMenu extends BaseAdapter {

    // Context
    private Activity mActivity;
    private List<MenuItem> mListMenuItems;
    private int mSelectedIndex;
    // Layout
    private LayoutInflater mLayoutInflater;


    public AdapterListMenu(Activity activity, List<MenuItem> listMenuItems) {
        mActivity = activity;
        mListMenuItems = listMenuItems;
        mLayoutInflater = mActivity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        if (mListMenuItems == null) {
            return 0;
        } else {
            return mListMenuItems.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if (mListMenuItems == null) {
            return null;
        } else {
            return mListMenuItems.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_list_menu, parent, false);
            holder = new ViewHolder();
            holder.imgIcon = (ImageView) convertView.findViewById(R.id.item_img_icon);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.item_tv_title);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MenuItem menuItem = mListMenuItems.get(position);
        holder.imgIcon.setImageResource(menuItem.getIconResource());
        holder.tvTitle.setText(menuItem.getTitle());
        if (position == mSelectedIndex) {
            convertView.setBackgroundResource(R.color.background_menu_drawer);
        } else {
            convertView.setBackgroundResource(android.R.color.transparent);
        }
        return convertView;
    }

    /**
     * Set selected index of DrawerMenu
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        mSelectedIndex = selectedIndex;
    }

    /**
     * Get selected index of DrawerMenu
     * @return
     */
    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    private class ViewHolder {
        ImageView imgIcon;
        TextView tvTitle;
    }
}
