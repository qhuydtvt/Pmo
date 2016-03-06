package vn.edu.techkids.pomodoro.entity;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import vn.edu.techkids.pomodoro.databases.PomodoroDbHelper;
import vn.edu.techkids.pomodoro.utils.TaskColor;

/**
 * Created by qhuydtvt on 12/9/2015.
 */
public class Task {

    /*====================================== DATABASE CONFIGS  ==================================*/
    public static final String TABLE_NAME = "task";

    /* COLUMN NAMES */
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PAYMENT = "payment";
    public static final String COLUMN_COLOR = "color";
    public static final String COLUMN_STATUS = "status";

    public static final String[] COLUMN_NAMES = new String[] {
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PAYMENT,
            COLUMN_COLOR,
            COLUMN_STATUS
    };

    /* QUERIES */
    public static final String SQL_CREATE_TABLE = String.format (
            "CREATE TABLE %s(" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s REAL NOT NULL," +
                    "%s TEXT," +
                    "%s INTEGER NOT NULL" +
                    ");",
            TABLE_NAME,
            COLUMN_ID,
            COLUMN_NAME,
            COLUMN_PAYMENT,
            COLUMN_COLOR,
            COLUMN_STATUS);

    public static final String SQL_DROP_TABLE = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);

    /*========================================= PROPERTIES  ======================================*/

    private long id;
    private String name;
    private float payment;
    private String color;
    private TaskStatus status;

    /*===================================== GETTERS & SETTERS  ==================================*/

    public Task(int id, String name, float payment, String color, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.payment = payment;
        this.color = color;
        this.status = status;
    }

    public Task(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID));
        this.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
        this.payment = cursor.getFloat(cursor.getColumnIndex(COLUMN_PAYMENT));
        this.color = cursor.getString(cursor.getColumnIndex(COLUMN_COLOR));
        this.status = intToStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
    }

    public Task(String name, float payment, String color) {
        this(-1, name, payment, color, TaskStatus.INCOMPLETE);
    }


    public Task() {
        this(-1, "", 0.0f, TaskColor.VALUE_LIGHT_GREEN, TaskStatus.INCOMPLETE);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id == -1) {
        /* Allow setting id only when it has not been set before  */
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /*===================================== CONSTRUCTORS  ===================================*/

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setData(String name, float payment, String color) {
        this.name = name;
        this.payment = payment;
        this.color = color;
    }

    public ContentValues getContentValues() {
        ContentValues taskRecordValues = new ContentValues();
        taskRecordValues.put(COLUMN_PAYMENT, payment);
        taskRecordValues.put(COLUMN_COLOR, color);
        taskRecordValues.put(COLUMN_NAME, name);
        taskRecordValues.put(COLUMN_STATUS, statusToInt(status));
        return taskRecordValues;
    }

    private int statusToInt(TaskStatus status) {
        return status == TaskStatus.INCOMPLETE ? 0 : 1;
    }

    private TaskStatus intToStatus(int i) {
        return (i == 0) ? TaskStatus.INCOMPLETE : TaskStatus.COMPLETE;
    }


    public static final Task[] DUMMY_DATA = new Task[] {
        new Task("Create design", 5.5f, TaskColor.VALUE_INDIGO),
        new Task("Investigate view 2", 10, TaskColor.VALUE_DEEP_PURPLE),
        new Task("Create icons", 10.0f, TaskColor.VALUE_LIGHT_GREEN),
        new Task("Create screen view 2", 10.0f, TaskColor.VALUE_RED)
    };
}