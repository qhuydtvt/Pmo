package vn.edu.techkids.pomodoro.entity;

import android.database.Cursor;

/**
 * Created by qhuydtvt on 1/16/2016.
 */
public class WeeklyTaskIncomeSpecific extends WeeklyTaskIncome {

    public WeeklyTaskIncomeSpecific(Cursor cursor) {
        super(cursor);
        mExtraData = new TaskIncomeSpecific(cursor);
    }
}
