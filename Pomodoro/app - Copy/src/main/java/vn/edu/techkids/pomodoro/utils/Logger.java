package vn.edu.techkids.pomodoro.utils;

import android.util.Log;

public final class Logger {

	private static final String TAG_NAME = "Pomodoro";
    // The stack position at which logging method of Logger class is called
    private static final int STACK_INDEX = 5;

    private static final String COLON = ":";

    private static final String DOT_SPACE = ". ";

    private Logger() {
    }

    private static StackTraceElement getCurrentStackElementAt(int idx) {
        return Thread.currentThread().getStackTrace()[idx];
    }

    private static String makeLogString(String msg) {
        StackTraceElement elem = getCurrentStackElementAt(STACK_INDEX);

        StringBuilder sb = new StringBuilder();
        sb.append(elem.getFileName());
        sb.append(COLON);
        sb.append(elem.getLineNumber());
        sb.append(DOT_SPACE);
        sb.append(msg);
        return sb.toString();
    }
    
    private static String makeLogString(String method, String msg) {
        StackTraceElement elem = getCurrentStackElementAt(STACK_INDEX);

        StringBuilder sb = new StringBuilder();
        sb.append(elem.getFileName());
        sb.append(COLON);
        sb.append(elem.getLineNumber());
        sb.append(DOT_SPACE);
        sb.append(method);
        sb.append("(): ");
        sb.append(msg);
        return sb.toString();
    }

    public static void i(String msg) {
        Log.i(TAG_NAME, makeLogString(msg));
    }
    
    public static void i(String method, String msg) {
        Log.i(TAG_NAME, makeLogString(method, msg));
    }    

    public static void d(String msg) {
        Log.d(TAG_NAME, makeLogString(msg));
    }
    
    public static void d(String method, String msg) {
        Log.d(TAG_NAME, makeLogString(method, msg));
    }

    public static void v(String msg) {
        Log.v(TAG_NAME, makeLogString(msg));
    }
    
    public static void v(String method, String msg) {
        Log.v(TAG_NAME, makeLogString(method, msg));
    }

    public static void w(String msg) {
        Log.w(TAG_NAME, makeLogString(msg));
    }

    public static void w(String method, String msg) {
        Log.w(TAG_NAME, makeLogString(method, msg));
    }
    
    public static void w(String msg, Throwable e) {
        Log.w(TAG_NAME, makeLogString(msg), e);
    }
    
    public static void w(String method, String msg, Throwable e) {
        Log.w(TAG_NAME, makeLogString(method, msg), e);
    }

    public static void e(String msg) {
        Log.e(TAG_NAME, makeLogString(msg));
    }

    public static void e(String method, String msg) {
        Log.e(TAG_NAME, makeLogString(method, msg));
    }
    
    public static void e(String msg, Throwable e) {
        Log.e(TAG_NAME, makeLogString(msg), e);
    }
    
    public static void e(String method, String msg, Throwable e) {
        Log.e(TAG_NAME, makeLogString(method, msg), e);
    }
}