package vn.edu.techkids.pomodoro.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import vn.edu.techkids.pomodoro.utils.Utils;

/**
 * Created by TienVV on 12/8/15.
 */
public class TimerView extends View {

    public interface OnTimerListener {

        /**
         * Called when timer is created
         */
        void onTimerCreated();

        /**
         * Called when timer is started
         */
        void onTimerStarted();

        /**
         * Called when timer is paused
         */
        void onTimerPaused();

        /**
         * Called when timer is resumed
         */
        void onTimerResume();

        /**
         * Called when timer is finished
         */
        void onTimerFinished();
    }

    // ================== Constants ================== //
    private static final int TIME_DELAY = 1000; // 1 seconds
    private static final int START_ANGLE = -90;
    private static final int END_ANGLE = -360;

    // ================== Context ================== //
    private int totalTime = 1000;
    private int currentTime = 0;
    private boolean isRunning;
    private OnTimerListener mListener;
    // Color
    private int colorBorderCircle = Color.parseColor("#f5ee2e");
    private int colorMainCircle = Color.parseColor("#b2d6d2");
    private int colorText = Color.WHITE;
    private int colorTextBorder;
    // Size
    private float sizeBorderCircle = 5;
    private float sizeMainCircle = 80;

    private float angleCurrent = END_ANGLE;
    private float delta = 0;

    // Paint
    private RectF mRectF = new RectF();
    private TextPaint mTextPaint = new TextPaint();;
    private Paint mPaint = new Paint();

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            currentTime++;
            angleCurrent = angleCurrent + delta;
            if (currentTime >= totalTime || angleCurrent <= END_ANGLE) {
                currentTime = totalTime;
                angleCurrent = END_ANGLE;
                isRunning = false;
                mHandler.postDelayed(mRunnableFinishTimer, 10);
            } else {
                isRunning = true;
                mHandler.postDelayed(this, TIME_DELAY);
            }
            invalidate();
        }
    };

    private Runnable mRunnableFinishTimer = new Runnable() {
        @Override
        public void run() {
            if (mListener != null) {
                mListener.onTimerFinished();
            }
        }
    };

    public TimerView(Context context) {
        super(context);
        initPainters();
//        mHandler.postDelayed(mRunnable, TIME_DELAY);
    }

    public TimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPainters();
//        mHandler.postDelayed(mRunnable, TIME_DELAY);
    }

    public TimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPainters();

//        mHandler.postDelayed(mRunnable, TIME_DELAY);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mRectF.set(0 + sizeMainCircle / 2 + sizeBorderCircle, 0 + sizeMainCircle / 2 + sizeBorderCircle,
                MeasureSpec.getSize(widthMeasureSpec) - sizeMainCircle / 2 - sizeBorderCircle,
                MeasureSpec.getSize(widthMeasureSpec) - sizeMainCircle / 2 - sizeBorderCircle);
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw outer border circle
        mPaint.setStrokeWidth(sizeBorderCircle);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorBorderCircle);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - sizeBorderCircle / 2,
                                mPaint);

        // Draw main circle
        mPaint.setStrokeWidth(sizeMainCircle);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorMainCircle);

        canvas.drawArc(mRectF, START_ANGLE, angleCurrent, false, mPaint);

        // Draw inner border circle
        mPaint.setStrokeWidth(sizeBorderCircle);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(colorBorderCircle);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - sizeBorderCircle - sizeMainCircle,
                                mPaint);

        canvas.restore();
        // Draw text
        mTextPaint.setColor(colorText);
        String text = getDrawText();
        if (!TextUtils.isEmpty(text)) {
            float textHeight = mTextPaint.descent() + mTextPaint.ascent();
            canvas.drawText(text, (getWidth() - mTextPaint.measureText(text)) / 2.0f + getX(),
                    (getWidth() - textHeight) / 2.0f + getY(), mTextPaint);
        }
    }

    protected void initPainters() {
        mTextPaint.setColor(colorText);
        mTextPaint.setTextSize(100);
        mTextPaint.setAntiAlias(true);

        mPaint.setAntiAlias(true);

        delta = (float)360 / totalTime;
        angleCurrent = - 360 + ((float) currentTime / totalTime * 360);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    protected String getDrawText() {
        return Utils.seconds2time(totalTime - currentTime);
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
        this.invalidate();
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
        this.invalidate();
    }

    public void setColorBorderCircle(int colorBorderCircle) {
        this.colorBorderCircle = colorBorderCircle;
        this.invalidate();
    }

    public void setColorMainCircle(int colorMainCircle) {
        this.colorMainCircle = colorMainCircle;
        this.invalidate();
    }

    public void setColorText(int colorText) {
        this.colorText = colorText;
        this.invalidate();
    }

    public void setColorTextBorder(int colorTextBorder) {
        this.colorTextBorder = colorTextBorder;
        this.invalidate();
    }

    public void setSizeBorderCircle(int sizeBorderCircle) {
        this.sizeBorderCircle = sizeBorderCircle;
        this.invalidate();
    }

    public void setSizeMainCircle(int sizeMainCircle) {
        this.sizeMainCircle = sizeMainCircle;
        this.invalidate();
    }

    /**
     * Start timer
     */
    public void start() {
        mHandler.postDelayed(mRunnable, TIME_DELAY);
        isRunning = true;
    }

    /**
     * Pause timer
     */
    public void pause() {
        mHandler.removeCallbacks(mRunnable);
        isRunning = false;
    }

    /**
     * Stop timer and reset
     */
    public void stopAndReset() {
        mHandler.removeCallbacks(mRunnable);
        isRunning = false;
        currentTime = 0;
        angleCurrent = END_ANGLE;
        invalidate();;
    }

    /**
     * Get remaining time of timer
     * @return
     */
    public int getRemainingTime() {
        int remainingTime = totalTime - currentTime;
        if (remainingTime <= 0) {
            remainingTime = 0;
        }
        return remainingTime;
    }

    /**
     * Indicate timer is running or not
     * @return
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Set timer is running or not
     * @param isRunning
     */
    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    /**
     * Set listener for timer
     * @param listener
     */
    public void setOnTimerListener(OnTimerListener listener) {
        mListener = listener;
    }
}
