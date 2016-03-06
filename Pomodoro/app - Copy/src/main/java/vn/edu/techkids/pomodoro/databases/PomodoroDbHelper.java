package vn.edu.techkids.pomodoro.databases;

/*import android.content.ContentValues;*/
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import vn.edu.techkids.pomodoro.entity.MonthlyTaskIncome;
import vn.edu.techkids.pomodoro.entity.Task;
import vn.edu.techkids.pomodoro.entity.TaskIncome;
import vn.edu.techkids.pomodoro.entity.TaskRecord;
import vn.edu.techkids.pomodoro.utils.Logger;
/*import vn.edu.techkids.pomodoro.utils.TaskColor;
import vn.edu.techkids.pomodoro.utils.Utils;*/

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qhuydtvt on 12/5/2015.
 */
public class PomodoroDbHelper extends SQLiteOpenHelper {

    /* =================================== CONSTANTS ============================================ */
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "pomodoro.db";
    private static final boolean DB_DUMMY = true;

    /* =================================== CONTEXT ============================================ */
    private Context mContext;

    /* =================================== CONSTRUCTORS ===========================================*/
    public PomodoroDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updatePomodoroDatabse(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updatePomodoroDatabse(db, oldVersion, newVersion);
    }

    private void updatePomodoroDatabse(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            Logger.d("updatePomodoroDatabse", Task.SQL_CREATE_TABLE);
            db.execSQL(Task.SQL_CREATE_TABLE);
            db.execSQL(TaskRecord.SQL_CREATE_TABLE);
            if (DB_DUMMY) {
                insertDummyData(db);
            }
        } else {
            /* Reversed for future use */
        }
    }

    public void insertTask(Task task) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        insertTask(sqLiteDatabase, task);
    }

    public void updateTask(Task task) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        updateTask(sqLiteDatabase, task);
    }

    public void deleteTask(Task task) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        deleteTask(sqLiteDatabase, task);
    }

    private void insertTask(SQLiteDatabase sqLiteDatabase, Task task) {
        long taskId = sqLiteDatabase.insert(Task.TABLE_NAME, null, task.getContentValues());
        task.setId(taskId);
    }

    private void updateTask(SQLiteDatabase sqLiteDatabase, Task task) {
        sqLiteDatabase.update(Task.TABLE_NAME, task.getContentValues(),
                String.format("%s = ?", Task.COLUMN_ID),
                new String[]{String.valueOf(task.getId())});
    }

    private void deleteTask(SQLiteDatabase sqLiteDatabase, Task task) {
        sqLiteDatabase.delete(Task.TABLE_NAME,
                String.format("%s = ?", Task.COLUMN_ID),
                new String[]{String.valueOf(task.getId())});
    }

    public List<Task> selectAllTasks() {
        ArrayList<Task> taskArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(Task.TABLE_NAME,
                Task.COLUMN_NAMES, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Task task = new Task(cursor);
            taskArrayList.add(task);
        }

        cursor.close();

        return taskArrayList;
    }

    public List<TaskIncome> selectAllMonthlyTaskIncomes() {
        ArrayList<TaskIncome> taskIncomeArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(MonthlyTaskIncome.SQL_SELECT, null);
        Logger.d("selectAllMonthlyTaskIncomes", MonthlyTaskIncome.SQL_SELECT);
        while(cursor.moveToNext()) {
            MonthlyTaskIncome monthlyTaskIncome = new MonthlyTaskIncome(cursor);
            taskIncomeArrayList.add(monthlyTaskIncome);
        }
        if(cursor != null
                 && !cursor.isClosed()) {
            cursor.close();
        }
        return taskIncomeArrayList;
    }

    public Cursor getMonthlyTaskIncomeCursor() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(MonthlyTaskIncome.SQL_SELECT, null);
        return cursor;
    }

    public Cursor getCursor(String sql) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        return cursor;
    }

    /**
     * Insert new TaskRecord
     * @param taskRecord data to insert
     */
    public void insertTaskRecord(TaskRecord taskRecord) {
        SQLiteDatabase db = this.getWritableDatabase();
        insertTaskRecord(db, taskRecord);
    }

    private void insertTaskRecord(SQLiteDatabase db, TaskRecord taskRecord) {
        long taskRecordId = db.insert(TaskRecord.TABLE_NAME, null, taskRecord.getContentValues());
        taskRecord.setId(taskRecordId);
        if (taskRecordId <= 0) {
            Logger.w("insertTaskRecord", "Insert task record failed. TaskRecordId = " + taskRecord.getTaskId());
        } else {
            Logger.i("insertTaskRecord", "Insert task record success. TaskRecordId = " + taskRecord.getTaskId());
        }
    }

    /**
     * Get all task records
     * @return
     */
    public List<TaskRecord> getAllTaskRecords() {
        ArrayList<TaskRecord> taskArrayList = new ArrayList<>();
        SQLiteDatabase sqliteDatabase = this.getReadableDatabase();

        String orderByClause = String.format("%s ASC", TaskRecord.COLUMN_RECORD_TIME);

        Cursor cursor = sqliteDatabase.query(TaskRecord.TABLE_NAME,
                TaskRecord.COLUMN_NAMES, null, null, null, null,
                orderByClause);

        while (cursor.moveToNext()) {
            TaskRecord task = new TaskRecord(cursor);
            taskArrayList.add(task);
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        Logger.d("getAllTaskRecords", "Count = " + taskArrayList.size());



        return taskArrayList;
    }

    /**
     * Get all task in period of time
     * @param fromDate
     * @param toDate
     * @return
     */
    public List<TaskRecord> getAllTaskRecords(String fromDate, String toDate) {
        ArrayList<TaskRecord> taskArrayList = new ArrayList<TaskRecord>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        if (fromDate.length() == 10) {
            fromDate = fromDate + " 00:00";
        }
        if (toDate.length() == 10) {
            toDate = toDate + " 00:00";
        }

        String whereClause = String.format("%s BETWEEN \'%s\' AND \'%s\'",
                TaskRecord.COLUMN_RECORD_TIME,
                fromDate,
                toDate);

        String orderByClause = String.format("%s ASC", TaskRecord.COLUMN_RECORD_TIME);

       /*TaskRecord.COLUMN_RECORD_TIME + ">= " + Utils.getMillisFromDate(fromDate) + " AND "+
                       TaskRecord.COLUMN_RECORD_TIME + "<= " + Utils.getMillisFromDate(toDate);*/

        Logger.d("getAllTaskRecords", "where = " + whereClause);

        Cursor cursor = sqLiteDatabase.query(TaskRecord.TABLE_NAME,
                TaskRecord.COLUMN_NAMES,
                whereClause, null, null, null, orderByClause);

        while (cursor.moveToNext()) {
            TaskRecord task = new TaskRecord(cursor);
            taskArrayList.add(task);
        }

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }

        Logger.d("getAllTaskRecords", "Count = " + taskArrayList.size());
        return taskArrayList;
    }

    private void insertDummyData(SQLiteDatabase db) {

        for (Task task : Task.DUMMY_DATA) {
            insertTask(db, task);
        }

        for(TaskRecord taskRecord : TaskRecord.DUMMY_DATA) {
            insertTaskRecord(db, taskRecord);
        }
    }
}