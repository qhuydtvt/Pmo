package vn.edu.techkids.pomodoro.entity;

import android.database.Cursor;

import vn.edu.techkids.pomodoro.utils.Logger;

/**
 * Created by qhuydtvt on 12/28/2015.
 */
public class MonthlyTaskIncome extends TaskIncome {

    protected int mMonth;

    public static final String SQL_SELECT = TaskIncome.SQL_SELECT +
            String.format(" GROUP BY %s || %s || %s" +
                            " ORDER BY %s || %s DESC",
                    TaskIncomeSpecific.COLUMN_TASK_ID, TaskIncome.COLUMN_YEAR_OF_RECORD, TaskIncome.COLUMN_MONTH_OF_RECORD,
                    TaskIncome.COLUMN_YEAR_OF_RECORD, TaskIncome.COLUMN_MONTH_OF_RECORD);

    public MonthlyTaskIncome(int totalDuration, float totalPayment, int year, int month) {
        super(totalDuration, totalPayment, year);
        this.mMonth = month;
    }

    public MonthlyTaskIncome(Cursor cursor) {
        super(cursor);
        this.mMonth = Integer.valueOf(cursor.getString(cursor.getColumnIndex(TaskIncome.COLUMN_MONTH_OF_RECORD)));
    }

    public int getMonth() { return  mMonth; }
}
