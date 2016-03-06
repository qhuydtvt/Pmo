package vn.edu.techkids.pomodoro.setting;

import java.util.ArrayList;
import java.util.List;

import vn.edu.techkids.pomodoro.R;

/**
 * Created by TienVV on 12/27/15.
 */
public class SettingItem {

    public static final int ID_WORKING_TIME = 1;
    public static final int ID_SHORT_BREAK_TIME = 2;
    public static final int ID_LONG_BREAK_TIME = 3;
    public static final int ID_LONG_BREAK_AFTER = 4;

    private boolean isCatergory;
    private int id;
    private int titleRes;
    private String description;

    public SettingItem(boolean isCatergory, int id, int titleRes, String description) {
        this.isCatergory = isCatergory;
        this.id = id;
        this.titleRes = titleRes;
        this.description = description;
    }

    public boolean isCatergory() {
        return isCatergory;
    }

    public void setIsCatergory(boolean isCatergory) {
        this.isCatergory = isCatergory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public void setTitleRes(int titleRes) {
        this.titleRes = titleRes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
