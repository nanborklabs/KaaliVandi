package com.kaalivandi.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Nandha on 9/23/2016.
 */

public class Iosthin extends TextView {
    public Iosthin(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public Iosthin(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public Iosthin(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/ios_thin.ttf", context);
        setTypeface(customFont);
    }
}
