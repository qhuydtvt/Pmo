package vn.edu.techkids.pomodoro.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.setting.SettingItem;

/**
 * Created by TienVV on 12/27/15.
 */
public class AdapterListSettingItems extends BaseAdapter {

    // Context
    private List<SettingItem> mListSettingItems;
    private LayoutInflater mLayoutInflater;

    public AdapterListSettingItems(Activity activity, List<SettingItem> listSettingItems) {
        mLayoutInflater = activity.getLayoutInflater();
        mListSettingItems = listSettingItems;
    }

    @Override
    public int getCount() {
        return mListSettingItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mListSettingItems.get(position);
    }

    /**
     * Get setitng item by id
     * @param id
     * @return
     */
    public SettingItem getItemById(int id) {
        SettingItem settingItem = null;
        for (SettingItem item : mListSettingItems) {
            if (item.getId() == id) {
                settingItem = item;
                break;
            }
        }
        return settingItem;
    }

    @Override
    public long getItemId(int position) {
        return mListSettingItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_list_setting_item, parent, false);

            holder = new ViewHolder();
            holder.lnrCategory = (LinearLayout) convertView.findViewById(R.id.lnr_category_title);
            holder.tvCategory = (TextView) convertView.findViewById(R.id.item_tv_setting_category);

            holder.lnrContent = (LinearLayout) convertView.findViewById(R.id.lnr_content);
            holder.tvTitle = (TextView) convertView.findViewById(R.id.item_tv_setting_title);
            holder.tvDescription = (TextView) convertView.findViewById(R.id.item_tv_setting_description);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SettingItem item = (SettingItem) getItem(position);
        if (item.isCatergory()) {
            holder.lnrCategory.setVisibility(View.VISIBLE);
            holder.lnrContent.setVisibility(View.GONE);

            holder.tvCategory.setText(item.getTitleRes());
        } else {
            holder.lnrCategory.setVisibility(View.GONE);
            holder.lnrContent.setVisibility(View.VISIBLE);

            holder.tvTitle.setText(item.getTitleRes());
            holder.tvDescription.setText(item.getDescription());
        }
        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        SettingItem item = (SettingItem) getItem(position);
        if (item.isCatergory()) {
            return false;
        } else {
            return true;
        }
    }

    private class ViewHolder {
        LinearLayout lnrCategory;
        TextView tvCategory;

        LinearLayout lnrContent;
        TextView tvTitle;
        TextView tvDescription;
    }
}
