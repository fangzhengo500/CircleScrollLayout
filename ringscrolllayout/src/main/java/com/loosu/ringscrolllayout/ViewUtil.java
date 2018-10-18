package com.loosu.ringscrolllayout;

import android.view.View;

public class ViewUtil {
    public static String MeasureSpecMode2String(int mode) {
        String str = "unknown measure spec mode";
        switch (mode){
            case View.MeasureSpec.UNSPECIFIED:
                str = "MeasureSpec.UNSPECIFIED";
                break;

            case View.MeasureSpec.EXACTLY:
                str = "MeasureSpec.EXACTLY";
                break;

            case View.MeasureSpec.AT_MOST:
                str = "MeasureSpec.AT_MOST";
                break;
        }
        return str;
    }
}
