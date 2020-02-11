package com.gmail.baric0748;

//custom webView widget

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class TouchyWebView extends WebView {

    public TouchyWebView(Context context)
    {
        super(context);
    }

    public TouchyWebView(Context context, AttributeSet attr)
    {
        super(context, attr);
    }

    public TouchyWebView(Context context, AttributeSet attr, int defStyle)
    {
        super(context, attr, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.findPointerIndex(0) == -1)
        {
            return super.onTouchEvent(event);
        }

        if(event.getPointerCount() >= 2)
        {
            requestDisallowInterceptTouchEvent(true);
        }
        else
        {
            requestDisallowInterceptTouchEvent(false);
        }

        return super.onTouchEvent(event);
    }
}
