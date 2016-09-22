package com.kaalivandi.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Nandha on 9/22/2016.
 */

public class IosMed extends TextView {
    public IosMed(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public IosMed(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public IosMed(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/ios_med.ttf", context);
        setTypeface(customFont);
    }
}
