package vn.edu.techkids.pomodoro.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.techkids.pomodoro.R;
import vn.edu.techkids.pomodoro.adapter.AdapterListSettingItems;
import vn.edu.techkids.pomodoro.setting.SettingItem;
import vn.edu.techkids.pomodoro.utils.Constants;
import vn.edu.techkids.pomodoro.utils.Logger;
import vn.edu.techkids.pomodoro.utils.PreferenceUtils;

/**
 * Created by TienVV on 12/27/15.
 */
public class FragmentSettings extends BaseFragment implements AdapterView.OnItemClickListener {

    // Context
    private View mView;
    private List<SettingItem> mListSettingItems;
    private AdapterListSettingItems mAdapterListSettingItems;
    private int selectedPos;
    // Layout
    private ListView mLvListSettingItems;
    private AlertDialog.Builder mBuilderAlert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initiate context
        initContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_setting, container, false);
        // Initiate layout
        initLayout();
        return mView;
    }

    /**
     * Initiate context
     */
    private void initContext() {
        mListSettingItems = new ArrayList<SettingItem>();
        // Category Time
        mListSettingItems.add(new SettingItem(true, 0, R.string.setting_catergory_time, null));
        // Item working time
        int workingTime = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME, Constants.SETTING_DEFAULT_WORKING_TIME);
        String desWorkingTime = getString(R.string.setting_common_minutes, workingTime / 60);
        mListSettingItems.add(new SettingItem(false, SettingItem.ID_WORKING_TIME, R.string.setting_item_working_time, desWorkingTime));
        // Item short break time
        int shortBreakTime = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_SHORT_BREAK, Constants.SETTING_DEFAULT_SHORT_BREAK_TIME);
        String desShortBreak = getString(R.string.setting_common_minutes, shortBreakTime / 60);
        mListSettingItems.add(new SettingItem(false, SettingItem.ID_SHORT_BREAK_TIME, R.string.setting_item_short_break, desShortBreak));
        // Item long break time
        int longBreakTime = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_LONG_BREAK, Constants.SETTING_DEFAULT_LONG_BREAK_TIME);
        String desLongBreak = getString(R.string.setting_common_minutes, longBreakTime / 60);
        mListSettingItems.add(new SettingItem(false, SettingItem.ID_LONG_BREAK_TIME, R.string.setting_item_long_break, desLongBreak));
        // Item long break after
        int longBreakAfter = mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_LONG_BREAK_AFTER, Constants.SETTING_DEFAULT_LONG_BREAK_AFTER);
        String desLongBreakAfter = getString(R.string.setting_common_breats, longBreakAfter);
        mListSettingItems.add(new SettingItem(false, SettingItem.ID_LONG_BREAK_AFTER, R.string.setting_item_long_break_after, desLongBreakAfter));

        mAdapterListSettingItems = new AdapterListSettingItems(getActivity(), mListSettingItems);

        mBuilderAlert = new AlertDialog.Builder(getActivity());
        mBuilderAlert.setCancelable(false);
    }

    /**
     * Initiate layout
     */
    private void initLayout() {
        mToolbar.setTitle(R.string.title_screen_settings);
        mFloatingActionButton.hide();

        mLvListSettingItems = (ListView) mView.findViewById(R.id.lv_list_setting_items);
        mLvListSettingItems.setAdapter(mAdapterListSettingItems);
        mLvListSettingItems.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SettingItem settingItem = (SettingItem) mAdapterListSettingItems.getItem(position);
        Logger.d("onItemClick", "item = " + getString(settingItem.getTitleRes()));
        switch (settingItem.getId()) {
            case SettingItem.ID_WORKING_TIME:
                settingWorkingTime();
                break;
            case SettingItem.ID_SHORT_BREAK_TIME:
                settingShortBreakTime();
                break;
            case SettingItem.ID_LONG_BREAK_TIME:
                settingLongBreakTime();
                break;
            case SettingItem.ID_LONG_BREAK_AFTER:
                settingLongBreakAfter();
                break;
        }
    }

    /**
     * Setting menu item Working time
     */
    private void settingWorkingTime() {
        mBuilderAlert.setTitle(R.string.setting_item_working_time);
        final int[] timeArr = getResources().getIntArray(R.array.setting_working_time);
        String[] options = new String[timeArr.length];
        selectedPos = 0;
        for (int i = 0; i < timeArr.length; i++) {
            options[i] = getString(R.string.setting_common_minutes, timeArr[i]);
            if (timeArr[i] * 60 == mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME,
                                                Constants.SETTING_DEFAULT_WORKING_TIME)) {
                selectedPos = i;
            }
        }
        mBuilderAlert.setSingleChoiceItems(options, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedPos = which;
            }
        });
        mBuilderAlert.setPositiveButton(R.string.common_alert_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d("onClick", "settingWorkingTime -> selected: " + selectedPos);
                mPreferenceUtils.setSetting(PreferenceUtils.KEY_SETTING_WORKING_TIME, timeArr[selectedPos] * 60);
                SettingItem settingItem = mAdapterListSettingItems.getItemById(SettingItem.ID_WORKING_TIME);
                settingItem.setDescription(getString(R.string.setting_common_minutes, timeArr[selectedPos]));
                mAdapterListSettingItems.notifyDataSetChanged();
            }
        });
        mBuilderAlert.setNegativeButton(R.string.common_alert_cancel, null);
        mBuilderAlert.create().show();
    }

    /**
     * Setting menu item Short break time
     */
    private void settingShortBreakTime() {
        mBuilderAlert.setTitle(R.string.setting_item_short_break);
        final int[] timeArr = getResources().getIntArray(R.array.setting_short_break);
        String[] options = new String[timeArr.length];
        selectedPos = 0;
        for (int i = 0; i < timeArr.length; i++) {
            options[i] = getString(R.string.setting_common_minutes, timeArr[i]);
            if (timeArr[i] * 60 == mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_SHORT_BREAK,
                    Constants.SETTING_DEFAULT_SHORT_BREAK_TIME)) {
                selectedPos = i;
            }
        }
        mBuilderAlert.setSingleChoiceItems(options, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedPos = which;
            }
        });
        mBuilderAlert.setPositiveButton(R.string.common_alert_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d("onClick", "settingShortBreakTime -> selected: " + selectedPos);
                mPreferenceUtils.setSetting(PreferenceUtils.KEY_SETTING_SHORT_BREAK, timeArr[selectedPos] * 60);
                SettingItem settingItem = mAdapterListSettingItems.getItemById(SettingItem.ID_SHORT_BREAK_TIME);
                settingItem.setDescription(getString(R.string.setting_common_minutes, timeArr[selectedPos]));
                mAdapterListSettingItems.notifyDataSetChanged();
            }
        });
        mBuilderAlert.setNegativeButton(R.string.common_alert_cancel, null);
        mBuilderAlert.create().show();
    }

    /**
     * Setting menu item Long break time
     */
    private void settingLongBreakTime() {
        mBuilderAlert.setTitle(R.string.setting_item_long_break);
        final int[] timeArr = getResources().getIntArray(R.array.setting_long_break);
        String[] options = new String[timeArr.length];
        selectedPos = 0;
        for (int i = 0; i < timeArr.length; i++) {
            options[i] = getString(R.string.setting_common_minutes, timeArr[i]);
            if (timeArr[i] * 60 == mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_LONG_BREAK,
                    Constants.SETTING_DEFAULT_LONG_BREAK_TIME)) {
                selectedPos = i;
            }
        }
        mBuilderAlert.setSingleChoiceItems(options, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedPos = which;
            }
        });
        mBuilderAlert.setPositiveButton(R.string.common_alert_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d("onClick", "settingLongBreakTime -> selected: " + selectedPos);
                mPreferenceUtils.setSetting(PreferenceUtils.KEY_SETTING_LONG_BREAK, timeArr[selectedPos] * 60);
                SettingItem settingItem = mAdapterListSettingItems.getItemById(SettingItem.ID_LONG_BREAK_TIME);
                settingItem.setDescription(getString(R.string.setting_common_minutes, timeArr[selectedPos]));
                mAdapterListSettingItems.notifyDataSetChanged();
            }
        });
        mBuilderAlert.setNegativeButton(R.string.common_alert_cancel, null);
        mBuilderAlert.create().show();
    }

    /**
     * Setting menu item Long break after
     */
    private void settingLongBreakAfter() {
        mBuilderAlert.setTitle(R.string.setting_item_long_break_after);
        final int[] timeArr = getResources().getIntArray(R.array.setting_long_break_after);
        String[] options = new String[timeArr.length];
        selectedPos = 0;
        for (int i = 0; i < timeArr.length; i++) {
            options[i] = getString(R.string.setting_common_breats, timeArr[i]);
            if (timeArr[i] == mPreferenceUtils.getSetting(PreferenceUtils.KEY_SETTING_LONG_BREAK_AFTER,
                    Constants.SETTING_DEFAULT_LONG_BREAK_AFTER)) {
                selectedPos = i;
            }
        }
        mBuilderAlert.setSingleChoiceItems(options, selectedPos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedPos = which;
            }
        });
        mBuilderAlert.setPositiveButton(R.string.common_alert_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Logger.d("onClick", "settingLongBreakAfter -> selected: " + selectedPos);
                mPreferenceUtils.setSetting(PreferenceUtils.KEY_SETTING_LONG_BREAK_AFTER, timeArr[selectedPos]);
                SettingItem settingItem = mAdapterListSettingItems.getItemById(SettingItem.ID_LONG_BREAK_AFTER);
                settingItem.setDescription(getString(R.string.setting_common_breats, timeArr[selectedPos]));
                mAdapterListSettingItems.notifyDataSetChanged();
            }
        });
        mBuilderAlert.setNegativeButton(R.string.common_alert_cancel, null);
        mBuilderAlert.create().show();
    }
}
