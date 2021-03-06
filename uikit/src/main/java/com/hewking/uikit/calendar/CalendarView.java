package com.hewking.uikit.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hewking.uikit.R;

import java.util.Arrays;

/**
 * Created by hewking on 2016/12/11.
 */
public class CalendarView extends View {


    private String TAG = "CalendarView";
    // 上下文
    private Context context;

    // view的宽度
    private int mViewWidth;

    // view的高度
    private int mViewHeight;

    // 日历背景
    private int calendarBg = 0xfff9f9f9;

    // 普通字体颜色
    private int mNormalTextColor = 0xff000000;

    // 选中字体的颜色
    private int mBeSelectedTextColor = 0xffffffff;

    // 不可选字体的颜色
    private int mUnBeSelectedTextColor = 0xff999999;

    // 不可选日期的背景
    private int mUnBeSelectedTextBgColor = 0xff000000;

    // 可选日期的背景
    private int mBeSelectedTextBgColor = 0xffff4000;

    // z正常日期背景
    private int mNormalTextBgColor = 0xffffffff;

    // 日期背景半径大小
    private int mBgRadius = 20;

    // 画笔
    private Paint mPaint;

    // 日字体大小
    private int mDayTextsize = 25;

    //当天
    private int mCurrentDay = 10;

    private int[] mSignUped = {8,9,14};



    public CalendarView(Context context) {
        this(context,null);

    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initAttributes(attrs);
    }

    private void initAttributes(AttributeSet attrs) {
        if (attrs == null) {
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);
        mBeSelectedTextColor = typedArray.getColor(R.styleable.CalendarView_selected_color, Color.RED);
        mUnBeSelectedTextColor = typedArray.getColor(R.styleable.CalendarView_unselected_color, Color.GRAY);
        typedArray.recycle();
    }

    /**
     * 初始化方法
     *
     * @param context
     */
    private void init(Context context) {
        this.context = context;
        mPaint = new Paint();

    }



    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewHeight = h;
        mViewWidth = w;
    }

    private int measureWidth(int widthMeasureSpec) {
        int width;

        int mode = MeasureSpec.getMode(widthMeasureSpec);

        int size = MeasureSpec.getSize(widthMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            // 不是精确模式的话得自己结合paddin
            int desire = size + getPaddingLeft() + getPaddingRight();
            if (mode == MeasureSpec.AT_MOST) {
                width = Math.min(desire, size);
            } else {
                width = desire;
            }
        }
        mViewWidth = width;

        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height;

        int mode = MeasureSpec.getMode(heightMeasureSpec);

        int size = MeasureSpec.getSize(heightMeasureSpec);

        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            // 不是精确模式的话得自己结合paddin
            int desire = size + getPaddingTop() + getPaddingBottom();
            if (mode == MeasureSpec.AT_MOST) {
                height = Math.min(desire, size);
            } else {
                height = desire;
            }
        }
        mViewHeight = height;
        return height;
    }


    float xInterval;
    float yInterval;

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Log.e(TAG, "mViewWidth=" + mViewWidth);
        Log.e(TAG, "mViewHeight=" + mViewHeight);
        canvas.drawColor(calendarBg);

        int hang_num = 3;
        int lie_num = 7;

        xInterval = mViewWidth / lie_num;
        yInterval = mViewHeight / hang_num;
        //背景圆的半径
        mBgRadius = (int) (Math.min(xInterval, yInterval) / 3);
        mDayTextsize = Math.min(mViewHeight, mViewWidth) / 15;
        mPaint.reset();
        drawDay(canvas);

    }


    /**
     * 绘制天
     *
     * @param canvas
     */
    private void drawDay(Canvas canvas) {
        mPaint.setTextSize(mDayTextsize);
        mPaint.setAntiAlias(true);
        float offset = 0;
        float x, y;
        int day = 0;
        // 绘制的日期
        String str;
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 8; j++) {
                day ++;
                str = day + "";
                Log.e(TAG, "string =" + str);
                offset = mPaint.measureText(str);
                x = j * xInterval - xInterval / 2;
                // 因为绘制在第一行
                y = i * yInterval - yInterval / 2;
                drawDayText(x, y, str, mNormalTextColor,isCurrentDay(day),isSignUped(day), canvas, offset);
            }
        }

    }

    private boolean isSignUped(int day) {
        return Arrays.binarySearch(mSignUped,day) >= 0 ;
    }

    private boolean isCurrentDay(int day) {
        return mCurrentDay == day;
    }


    private void drawDayText(float x, float y, String text, int textColor, boolean isToday,boolean isSignup,Canvas canvas, float offset) {
        mPaint.reset();
        mPaint.setTextSize(mDayTextsize);
        mPaint.setAntiAlias(true);
        mPaint.reset();
        mPaint.setTextSize(mDayTextsize);
        mPaint.setAntiAlias(true);



        Rect lRect = new Rect();
        // 获取text所在rect 的宽高
        mPaint.getTextBounds(text,0,text.length(),lRect);

        drawDayBg(x, y, isToday,isSignup, canvas, offset,lRect.height() / 2);


        mPaint.setColor(textColor);
        mPaint.setAntiAlias(true);
        if(isToday){
            mPaint.setColor(mBeSelectedTextColor);
        }
        canvas.drawText(text, x - offset / 2, y, mPaint);
    }

    private void drawDayBg(float x, float y, boolean isToday,boolean isSignUp, Canvas canvas, float offset,int yOffset) {
        // TODO Auto-generated method stub
        mPaint.setStyle(Paint.Style.FILL);
        if (isToday) {
            mPaint.setColor(getResources().getColor(R.color.colorAccent));
            canvas.drawCircle(x , y - yOffset , mBgRadius, mPaint);
            return;
        }

        if(isSignUp){

        }else{
            mPaint.setColor(mUnBeSelectedTextColor);
        }
        canvas.drawCircle(x , y + mBgRadius - yOffset , mBgRadius / 5, mPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // 获取事件的位置
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                    // 以下是对日历的事件处理
                    int theX = (int) ((touchX) / xInterval);// 获取第几列
                    int theY = (int) ((touchY - yInterval / 4) / yInterval);// 获取第几行
                    Log.e("click ds", "第" + theX + "列");
                    Log.e("click ds", "第" + theY + "行");
                    int theDay = theY * 7 + theX + 1;
                Log.e("click ds", theDay + " : the day ");
//                    // 得到是哪一天
//                    int num = (theY - 1) * 7 + theX - weekOfFirstDay;
//                    int day;
//                    if (num < 0 || num > allDays.length - 1) {
//                        num = -2;
//                        day = 0;
//                    } else {
//                        day = allDays[num];
//                    }


                    invalidate();
//                    int gapCount = CalendarUtils.getGapCount(year, month, day, NowYear, NowMonth, NowDay);
//                    if (!isOverdue(day, month, year)) {
//                        selectedDay = day;
//                        selectedMonth = month;
//                        selectedYear = year;
//                        if (onChooseListener != null) {
//                            onChooseListener.onSingleChoose(year, month, day, false, gapCount, theX + 1);
//
//                        }
//                        if (mHandler != null) {
//                            mHandler.sendEmptyMessageDelayed(0, 100);
//                        }
//                    } else {
//                        if (onChooseListener != null) {
//                            onChooseListener.onSingleChoose(year, month, day, true, gapCount, theX + 1);
//                        }
//
//                    }

                }
        return true;
    }


    public interface OnChooseListener {
        void onSingleChoose(int year, int month, int day, boolean overdue, int gapToday, int weekday);
    }

    private OnChooseListener onChooseListener;

    public OnChooseListener getOnChooseListener() {
        return onChooseListener;
    }

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

}
