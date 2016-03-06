package vn.edu.techkids.pomodoro.entity;

import android.database.Cursor;

/**
 * Created by qhuydtvt on 1/16/2016.
 */
public class TaskIncomeSpecific {

    private String taskName;
    private String taskColor;

    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_TASK_NAME = "task_name";
    public static final String COLUMN_TASK_COLOR = "task_color";

    public TaskIncomeSpecific(String taskName, String taskColor) {
        this.taskName = taskName;
        this.taskColor = taskColor;
    }

    public TaskIncomeSpecific(Cursor cursor) {
        taskName = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_NAME));
        taskColor = cursor.getString(cursor.getColumnIndex(COLUMN_TASK_COLOR));
    }

    public String getTaskName() {return taskName;}
    public String getTaskColor() {return taskColor;}
}
