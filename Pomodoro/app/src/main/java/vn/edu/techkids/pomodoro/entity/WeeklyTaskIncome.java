package vn.edu.techkids.pomodoro.entity;

import android.database.Cursor;

/**
 * Created by qhuydtvt on 1/16/2016.
 */
public class WeeklyTaskIncome extends TaskIncome {

    protected int mWeek;

    public static final String SQL_SELECT = TaskIncome.SQL_SELECT +
            String.format(" GROUP BY %s || %s || %s" +
                            " ORDER BY %s || %s DESC",
                    TaskIncomeSpecific.COLUMN_TASK_ID, TaskIncome.COLUMN_YEAR_OF_RECORD, TaskIncome.COLUMN_WEEK_OF_RECORD,
                    TaskIncome.COLUMN_YEAR_OF_RECORD, TaskIncome.COLUMN_WEEK_OF_RECORD);

    public WeeklyTaskIncome(int totalWorkDuration, float totalPayment, int year, int week) {
        super(totalWorkDuration, totalPayment, year);
        this.mWeek = week;
    }

    public WeeklyTaskIncome(Cursor cursor) {
        super(cursor);
        this.mWeek = cursor.getInt(cursor.getColumnIndex(COLUMN_WEEK_OF_RECORD));
    }

    public int getWeek() { return mWeek; }
}
