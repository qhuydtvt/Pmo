package vn.edu.techkids.pomodoro.entity;

import android.content.ContentValues;
import android.database.Cursor;

import vn.edu.techkids.pomodoro.utils.Logger;
import vn.edu.techkids.pomodoro.utils.Utils;

/**
 * Created by qhuydtvt on 12/10/2015.
 */
public class TaskRecord {

    /*====================================== DATABASE CONFIGS  ==================================*/
    public static final String TABLE_NAME = "task_record";

    /* COLUMNS */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TASK_ID = "task_id";
    public static final String COLUMN_RECORD_TIME = "record_time";
    public static final String COLUMN_WORK_DURATION = "work_duration";

    public static final String[] COLUMN_NAMES = new String[]{
            COLUMN_ID,
            COLUMN_TASK_ID,
            COLUMN_RECORD_TIME,
            COLUMN_WORK_DURATION
    };

    /* QUERIES */
    public static final String SQL_CREATE_TABLE = String.format(
            "CREATE TABLE %s (" +
            "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
            "%s INTEGER NOT NULL," +
            "%s TEXT NOT NULL," +
            "%s INTEGER NOT NULL" +
            ");",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_TASK_ID,
            COLUMN_RECORD_TIME,
            COLUMN_WORK_DURATION);

    /*========================================= PROPERTIES  ======================================*/
    private long id;
    private long taskId;
    private String recordTime;
    private int workDuration;

    /*===================================== GETTERS & SETTERS  ==================================*/

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if(id == -1) {
            /* Allow setting id only when it has not been set before  */
            this.id = id;
        }
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public int getWorkDuration() {
        return workDuration;
    }

    public void setWorkDuration(int workDuration) {
        this.workDuration = workDuration;
    }

    /*========================================= CONSTRUCTORS  ====================================*/

    public TaskRecord(long id, long taskId, String recordTime, int workDuration) {
        this.id = id;
        this.taskId = taskId;
        this.recordTime = recordTime;
        this.workDuration = workDuration;
    }

    public TaskRecord(long taskId, String recordTime, int workDuration) {
        this(-1, taskId, recordTime, workDuration);
    }

    public TaskRecord(Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        this.taskId = cursor.getInt(cursor.getColumnIndex(COLUMN_TASK_ID));
        this.recordTime = cursor.getString(cursor.getColumnIndex(COLUMN_RECORD_TIME));
        this.workDuration = cursor.getInt(cursor.getColumnIndex(COLUMN_WORK_DURATION));
        Logger.d("TaskRecord", "recordTime = " + recordTime);
    }

    public ContentValues getContentValues() {
        ContentValues retContentValues = new ContentValues();

        retContentValues.put(COLUMN_TASK_ID, taskId);
        retContentValues.put(COLUMN_RECORD_TIME, recordTime);
        retContentValues.put(COLUMN_WORK_DURATION, workDuration);

        return retContentValues;
    }

    public static final TaskRecord[] DUMMY_DATA = new TaskRecord[] {
            new TaskRecord(1, 1, Utils.getDatetimeString(2015, 12, 6, 6, 30), 25),
            new TaskRecord(4, 2, Utils.getDatetimeString(2015, 12, 6, 6, 30), 25),
            new TaskRecord(2, 3, Utils.getDatetimeString(2015, 6, 6, 9, 30), 25),
            new TaskRecord(3, 4, Utils.getDatetimeString(2015, 9, 6, 13, 30), 25),
            new TaskRecord(7, 1, Utils.getDatetimeString(2015, 12, 29, 9, 30), 25),
            new TaskRecord(5, 1, Utils.getDatetimeString(2015, 12, 15, 9, 30), 25),
            new TaskRecord(6, 2, Utils.getDatetimeString(2015, 12, 14, 9, 30), 25),
            new TaskRecord(6, 2, Utils.getDatetimeString(2015, 11, 30, 9, 30), 25),
            new TaskRecord(6, 3, Utils.getDatetimeString(2015, 7, 9, 10, 30), 25),
            new TaskRecord(10, 3, Utils.getDatetimeString(2015, 7, 23, 14, 30), 25),
            new TaskRecord(11, 4, Utils.getDatetimeString(2014, 7, 9, 19, 30), 25),
            new TaskRecord(12, 4, Utils.getDatetimeString(2014, 7, 26, 15, 00), 25),
            new TaskRecord(13, 4, Utils.getDatetimeString(2014, 7, 26, 17, 00), 25),
            new TaskRecord(14, 1, Utils.getDatetimeString(2014, 7, 24, 10, 15), 25),
    };
}
