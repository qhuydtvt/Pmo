package vn.edu.techkids.pomodoro.entity;

import android.database.Cursor;

/**
 * Created by qhuydtvt on 1/16/2016.
 */
public class MonthlyTaskIncomeSpecific extends MonthlyTaskIncome {

    public MonthlyTaskIncomeSpecific(Cursor cursor) {
        super(cursor);
        this.mMonth = Integer.valueOf(cursor.getString(cursor.getColumnIndex(TaskIncome.COLUMN_MONTH_OF_RECORD)));
        mExtraData = new TaskIncomeSpecific(cursor);
    }
}
