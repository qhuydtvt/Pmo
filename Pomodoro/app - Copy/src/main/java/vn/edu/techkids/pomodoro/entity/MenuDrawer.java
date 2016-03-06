package vn.edu.techkids.pomodoro.entity;

import java.util.ArrayList;
import java.util.List;

import vn.edu.techkids.pomodoro.R;

/**
 * Created by TienVV on 12/7/15.
 */
public class MenuDrawer {

    public static final int ID_LIST_TASK = 0;
    public static final int ID_TIMER = 1;
    public static final int ID_TASK_SUMMARY = 2;
    public static final int ID_INCOME = 3;
    public static final int ID_SETTING = 4;

    public static List<MenuItem> getListMenuItems() {
        List<MenuItem> listMenuItems = new ArrayList<MenuItem>();

        listMenuItems.add(new MenuItem(ID_LIST_TASK, R.string.menu_list_task, R.drawable.ic_menu_list_task));
        listMenuItems.add(new MenuItem(ID_TIMER, R.string.menu_timer, R.drawable.ic_menu_timer));
        listMenuItems.add(new MenuItem(ID_TASK_SUMMARY, R.string.menu_task_summary, R.drawable.ic_menu_task_summary));
        listMenuItems.add(new MenuItem(ID_INCOME, R.string.menu_income, R.drawable.ic_menu_salary));
        listMenuItems.add(new MenuItem(ID_SETTING, R.string.menu_setting, R.drawable.ic_menu_setting));
        return listMenuItems;
    }
}
