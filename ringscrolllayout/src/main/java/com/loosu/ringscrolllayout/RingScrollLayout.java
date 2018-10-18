package com.loosu.ringscrolllayout;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import androidx.core.view.GestureDetectorCompat;

public class RingScrollLayout extends ViewGroup {
    private static final String TAG = "RingScrollLayout";

    private GestureDetectorCompat mGestureDetector = null;

    private int mTouchSlop;
    private int mMinVelocity;
    private int mMaxVelocity;

    private Point mCenterPoint = new Point(Integer.MIN_VALUE, Integer.MIN_VALUE);
    private int mRadius = 100;
    private LayoutOrder mLayoutOrder = LayoutOrder.ANTICLOCKWISE;   // default layout anticlockwise.

    public RingScrollLayout(Context context) {
        super(context);
        init(context, null);
    }

    public RingScrollLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }


    public RingScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mGestureDetector = new GestureDetectorCompat(context, mOnGestureListener);

        ViewConfiguration config = ViewConfiguration.get(context);
        mTouchSlop = config.getScaledPagingTouchSlop();
        mMinVelocity = config.getScaledMinimumFlingVelocity();
        mMaxVelocity = config.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childMaxWidth = 0;
        int childMaxHeight = 0;
        final int widthSpec = widthSize - paddingLeft - paddingRight;
        final int heightSpec = heightSize - paddingTop - paddingBottom;

        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == View.GONE) {
                continue;
            }

            childAt.measure(MeasureSpec.makeMeasureSpec(widthSpec, MeasureSpec.AT_MOST /* this flag no useful, I want to known why */),
                    MeasureSpec.makeMeasureSpec(heightSpec, MeasureSpec.AT_MOST /* this flag no useful, I want to known why */));

            childMaxWidth = Math.max(childMaxWidth, childAt.getMeasuredWidth());
            childMaxHeight = Math.max(childMaxHeight, childAt.getMeasuredHeight());
        }

        int width = resolveAdjustedSize(childMaxWidth + paddingLeft + paddingRight, widthMeasureSpec);
        int height = resolveAdjustedSize(childMaxHeight + paddingTop + paddingBottom, heightMeasureSpec);

        int finalWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, widthMode);
        int finalHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, heightMode);
        setMeasuredDimension(finalWidthMeasureSpec, finalHeightMeasureSpec);
    }

    /**
     * calculate the size that final used
     *
     * @param desiredSize desired size of child
     * @param measureSpec measure spec of parent
     * @return final size
     */
    private int resolveAdjustedSize(int desiredSize, int measureSpec) {
        int result = 0;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.EXACTLY:
                // No choice. Do what we are told.
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                // MeasureSpec.UNSPECIFIED: parent say we can as big as we want.
                // MeasureSpec.AT_MOST: parent say we can no biger than measureSpec.
                // And we use the min spec.
                int childCount = getChildCount();
                if (childCount > 1) {
                    result = Math.min(size, desiredSize + mRadius * 2);
                } else {
                    result = Math.min(size, desiredSize);
                }
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mCenterPoint.x == Integer.MIN_VALUE && mCenterPoint.y == Integer.MIN_VALUE) {
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int paddingRight = getPaddingRight();
            int paddingBottom = getPaddingBottom();
            mCenterPoint.x = (w - paddingLeft - paddingRight) / 2 + paddingLeft;
            mCenterPoint.y = (h - paddingTop - paddingBottom) / 2 + paddingTop;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        //int centerX = (getMeasuredWidth() - paddingLeft - paddingRight) / 2 + paddingLeft;
        //int centerY = (getMeasuredHeight() - paddingTop - paddingBottom) / 2 + paddingTop;
        int centerX = mCenterPoint.x;
        int centerY = mCenterPoint.y;

        int childCount = getChildCount();
        if (childCount > 1) {
            for (int i = 0; i < childCount; i++) {

                double angle = -360 / childCount * i + 90;
                int cX = (int) (centerX - mRadius * Math.cos(Math.toRadians(angle)));
                int cY = (int) (centerY - mRadius * Math.sin(Math.toRadians(angle)));

                View childAt = getChildAt(i);

                int childWidth = childAt.getMeasuredWidth();
                int childHeight = childAt.getMeasuredHeight();

                int left = cX - childWidth / 2;
                int top = cY - childHeight / 2;
                int right = cX + childWidth / 2;
                int bottom = cY + childHeight / 2;
                childAt.layout(left, top, right, bottom);
            }
        } else if (childCount == 1) {
            View childAt = getChildAt(0);

            int childWidth = childAt.getMeasuredWidth();
            int childHeight = childAt.getMeasuredHeight();

            int left = paddingLeft;
            int top = paddingTop;
            int right = left + childWidth + paddingRight;
            int bottom = top + childHeight + paddingBottom;

            childAt.layout(left, top, right, bottom);
        } else {
            // do nothing
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //return super.onTouchEvent(event);
        return mGestureDetector.onTouchEvent(event);
    }

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
        requestLayout();
    }

    public int getCenterX() {
        return mRadius;
    }

    public void setCenterX(int centerX) {
        mCenterPoint.x = centerX;
        requestLayout();
    }

    public int getCenterY() {
        return mRadius;
    }

    public void setCenterY(int centerY) {
        mCenterPoint.y = centerY;
        requestLayout();
    }

    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        /**
         * @param e1        the event MotionEvent.ACTION_DOWN.
         * @param e2        current touch event.
         * @param distanceX distanceX
         * @param distanceY distanceY
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float aX = e1.getX();
            float aY = e1.getY();

            float moveX = e2.getX();
            float moveY = e2.getY();

            //float

            // calculate the angle we move
//            float angle = Math.toDegrees(Math.acos((mStart.first * end.first + mStart.second * end.second) / (Math.sqrt(mStart.first * mStart.first +
//                    mStart.second * mStart.second) * Math.sqrt(end.first * end.first + end.second * end.second))))
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e(TAG, "onFling: ");
            return true;
        }
    };

    public static double calculateAngle(double x, double y, double centerY, double centerX) {
        return  Math.atan((y-centerY)/(x-centerX));
    }

    /**
     * Layout order
     */
    public enum LayoutOrder {
        CLOCKWISE,      // clockwise 顺时针
        ANTICLOCKWISE  // anticlockwise 逆时针
    }


}
