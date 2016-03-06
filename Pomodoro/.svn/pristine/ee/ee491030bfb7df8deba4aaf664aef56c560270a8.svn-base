package vn.edu.techkids.pomodoro.entity;

import vn.edu.techkids.pomodoro.R;

/**
 * Created by TienVV on 12/20/15.
 */
public class TimePeriod {

    public static final int ID_THIS_WEEK = 1;
    public static final int ID_THIS_MONTH = 2;
    public static final int ID_THIS_YEAR = 3;
    public static final int ID_ALL = 4;
    public static final int ID_CUSTOM = 5;

    /**
     * Get list time periods
     * @return
     */
    public static TimePeriod[] getListTimePeriod() {
        TimePeriod[] timePeriods = new TimePeriod[] {
                new TimePeriod(ID_THIS_WEEK, R.string.time_this_week),
                new TimePeriod(ID_THIS_MONTH, R.string.time_this_month),
                new TimePeriod(ID_THIS_YEAR, R.string.time_this_year),
                new TimePeriod(ID_ALL, R.string.time_all),
                new TimePeriod(ID_CUSTOM, R.string.time_custom),
        };
        return timePeriods;
    }

    private int id;
    private int titleRes;

    private TimePeriod() {
        // Prevent create default constructor
    }

    public TimePeriod(int id, int title) {
        this.id = id;
        this.titleRes = title;
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

    public void setTitle(int title) {
        this.titleRes = title;
    }
}
