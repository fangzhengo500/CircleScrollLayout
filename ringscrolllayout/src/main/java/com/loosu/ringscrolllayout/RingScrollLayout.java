package com.loosu.ringscrolllayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class RingScrollLayout extends ViewGroup {
    private static final String TAG = "RingScrollLayout";

    private int mTouchSlop;
    private int mMinVelocity;
    private int mMaxVelocity;

    private int mRadius = 100;

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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int centerX = (getMeasuredWidth() - paddingLeft - paddingRight) / 2 + paddingLeft;
        int centerY = (getMeasuredHeight() - paddingTop - paddingBottom) / 2 + paddingTop;

        int childCount = getChildCount();
        if (childCount > 1) {
            for (int i = 0; i < childCount; i++) {

                double angle = 360 / childCount * i + 90;
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

    public int getRadius() {
        return mRadius;
    }

    public void setRadius(int radius) {
        mRadius = radius;
       requestLayout();
    }
}
