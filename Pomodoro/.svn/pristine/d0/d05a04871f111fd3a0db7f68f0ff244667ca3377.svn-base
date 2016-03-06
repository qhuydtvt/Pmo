package vn.edu.techkids.pomodoro.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by TienVV on 12/11/15.
 */
public class ProgressView extends View {

    // Context
    private int max = 10;
    private int current = 0;

    // Color
    private int colorBackground = Color.parseColor("#4DFFFFFF");
    private int colorProgress = Color.parseColor("#c69b3f");

    // Paint
    private Paint mPaint = new Paint();

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Draw background
        mPaint.setColor(colorBackground);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        // Draw progress
        mPaint.setColor(colorProgress);
        canvas.drawRect(0, 0, getWidth() * current / max, getHeight(), mPaint);
    }

    /**
     * Set max progress
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        max = maxProgress;
        super.invalidate();
    }

    /**
     * Set current progress
     * @param currentProgress
     */
    public void setCurrentProgress(int currentProgress) {
        this.current = currentProgress;
        super.invalidate();
    }
}
