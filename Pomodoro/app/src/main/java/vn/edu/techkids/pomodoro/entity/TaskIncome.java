package vn.edu.techkids.pomodoro.entity;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;

/**
 * Created by qhuydtvt on 12/27/2015.
 * Base Taskincome: Common information and actions for TaskIncome:
 */
public class TaskIncome {

    protected int mYear;
    protected int mTotalWorkDuration;
    protected float mTotalPayment;
    protected Object mExtraData;

    public static final String TAG_MONTHLY = "Months";
    public static final String TAG_WEEKLY = "Weeks";

/*    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_ID = "task_id";*/

    public static final String COLUMN_TOTAL_WORK_DURATION = "total_work_duration";
    public static final String COLUMN_TOTAL_PAYMENT = "total_payment";
    public static final String COLUMN_YEAR_OF_RECORD = "year_of_record";
    public static final String COLUMN_MONTH_OF_RECORD = "month_of_record";
    public static final String COLUMN_WEEK_OF_RECORD = "week_of_record";
    public static final String COLUMN_DAY_OF_RECORD = "day_of_record";

    /* COMMON SELECT QUERY, will not be used directly in DatabaseHelper,
    but used to create other queries */
    public static final String SQL_SELECT = String.format(
            "SELECT " +
                    "%s AS %s, " +
                    "%s AS %s, " +
                    "%s.%s AS %s, " +
                    "SUM(%s) AS %s, " +
                    "SUM(%s * %s) AS %s, " +
                    "STRFTIME('%%Y', %s) AS %s, " +
                    "STRFTIME('%%m', %s) AS %s, " +
                    "STRFTIME('%%w', %s) AS %s, " +
                    "STRFTIME('%%d', %s) AS %s " +
                    "FROM %s INNER JOIN %s " +
                    "ON %s.%s = %s.%s",

            /* Selected Columns */
            TaskRecord.COLUMN_TASK_ID, TaskIncomeSpecific.COLUMN_TASK_ID,
            Task.COLUMN_NAME, TaskIncomeSpecific.COLUMN_TASK_NAME,

            Task.TABLE_NAME, Task.COLUMN_COLOR, TaskIncomeSpecific.COLUMN_TASK_COLOR,

            TaskRecord.COLUMN_WORK_DURATION, COLUMN_TOTAL_WORK_DURATION,

            TaskRecord.COLUMN_WORK_DURATION, Task.COLUMN_PAYMENT, COLUMN_TOTAL_PAYMENT,

            TaskRecord.COLUMN_RECORD_TIME, COLUMN_YEAR_OF_RECORD,

            TaskRecord.COLUMN_RECORD_TIME, COLUMN_MONTH_OF_RECORD,

            TaskRecord.COLUMN_RECORD_TIME, COLUMN_WEEK_OF_RECORD,

            TaskRecord.COLUMN_RECORD_TIME, COLUMN_DAY_OF_RECORD,

            /* Clauses */
            TaskRecord.TABLE_NAME, Task.TABLE_NAME,
            TaskRecord.TABLE_NAME, TaskRecord.COLUMN_TASK_ID, Task.TABLE_NAME, Task.COLUMN_ID);

    public TaskIncome() {
    }

    public TaskIncome(int totalWorkDuration, float totalPayment, int year) {
        this.mTotalWorkDuration = totalWorkDuration;
        this.mTotalPayment = totalPayment;
        this.mYear = year;
    }

    public TaskIncome(Cursor cursor) {
        this.mTotalWorkDuration = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL_WORK_DURATION));
        this.mTotalPayment = cursor.getFloat(cursor.getColumnIndex(COLUMN_TOTAL_PAYMENT));
        this.mYear = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR_OF_RECORD));
    }

    public int getTotalDuration() {
        return mTotalWorkDuration;
    }

    public float getTotalPayment() {
        return mTotalPayment;
    }

    public int getYear() {
        return mYear;
    }

    public Object getExtraData() { return mExtraData; }

    public String getTitle() {return null;}

    public static List<TaskIncome> getMonthlyTaskIncomeList(PomodoroDbHelper pomodoroDbHelper) {

        Cursor cursor = pomodoroDbHelper.getCursor(MonthlyTaskIncome.SQL_SELECT);

        List<TaskIncome> retTaskIncomeList = new ArrayList<>();
        List<TaskIncome> taskIncomeBuffer = new ArrayList<>();

        int yearGroup = -1;
        int monthGroup = -1;
        int totalWorkdurationGroup = 0;
        float totalPaymentGroup = 0.0f;

        while (cursor.moveToNext()) {
            MonthlyTaskIncomeSpecific taskIncome = new MonthlyTaskIncomeSpecific(cursor);

            if (yearGroup == -1) {
                yearGroup = taskIncome.getYear();
                monthGroup = taskIncome.getMonth();
            }
            int taskIncomeYear = taskIncome.getYear();
            int taskIncomeMonth = taskIncome.getMonth();

            if (taskIncomeYear == yearGroup && taskIncomeMonth == monthGroup) {

            } else {
                MonthlyTaskIncome taskIncomeHeader = new MonthlyTaskIncome(
                        totalWorkdurationGroup, totalPaymentGroup, yearGroup, monthGroup);

                retTaskIncomeList.add(taskIncomeHeader);
                retTaskIncomeList.addAll(taskIncomeBuffer);

                taskIncomeBuffer.clear();
                totalWorkdurationGroup = 0;
                totalPaymentGroup = 0.0f;
                yearGroup = taskIncomeYear;
                monthGroup = taskIncomeMonth;
            }
            taskIncomeBuffer.add(taskIncome);
            totalWorkdurationGroup += taskIncome.getTotalDuration();
            totalPaymentGroup += taskIncome.getTotalPayment();
        }

        cursor.close();
        return retTaskIncomeList;
    }

    public static List<TaskIncome> getWeeklyTaskIncomeList (PomodoroDbHelper pomodoroDbHelper) {
        Cursor cursor = pomodoroDbHelper.getCursor(WeeklyTaskIncome.SQL_SELECT);

        List<TaskIncome> retTaskIncomeList = new ArrayList<>();
        List<TaskIncome> taskIncomeBuffer = new ArrayList<>();

        int yearGroup = -1;
        int weekGroup = -1;
        int totalWorkdurationGroup = 0;
        float totalPaymentGroup = 0.0f;

        while (cursor.moveToNext()) {
            WeeklyTaskIncomeSpecific taskIncome = new WeeklyTaskIncomeSpecific(cursor);

            if (yearGroup == -1) {
                yearGroup = taskIncome.getYear();
                weekGroup = taskIncome.getWeek();
            }

            int taskIncomeYear = taskIncome.getYear();
            int taskIncomeWeek = taskIncome.getWeek();

            if (taskIncomeYear == yearGroup && taskIncomeWeek == weekGroup) {

            } else {
                WeeklyTaskIncome taskIncomeHeader = new WeeklyTaskIncome(
                        totalWorkdurationGroup, totalPaymentGroup, yearGroup, weekGroup);

                retTaskIncomeList.add(taskIncomeHeader);
                retTaskIncomeList.addAll(taskIncomeBuffer);

                taskIncomeBuffer.clear();
                totalWorkdurationGroup = 0;
                totalPaymentGroup = 0.0f;
                yearGroup = taskIncomeYear;
                weekGroup = taskIncomeWeek;
            }
            taskIncomeBuffer.add(taskIncome);
            totalWorkdurationGroup += taskIncome.getTotalDuration();
            totalPaymentGroup += taskIncome.getTotalPayment();
        }

        cursor.close();
        return retTaskIncomeList;
    }

    public static List<TaskIncome> getTaskIncomeList(PomodoroDbHelper pomodoroDbHelper, String tag) {
        if (tag == TAG_MONTHLY) {
            return getMonthlyTaskIncomeList(pomodoroDbHelper);
        } else if (tag == TAG_WEEKLY) {
            return getWeeklyTaskIncomeList(pomodoroDbHelper);
        } else {
            return null;
        }
    }
}
