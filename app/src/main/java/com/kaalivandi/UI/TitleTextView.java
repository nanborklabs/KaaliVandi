package com.kaalivandi.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by nandhu on 4/9/16.
 */
public class TitleTextView extends TextView {

    public TitleTextView(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TitleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TitleTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fallingsky.otf", context);
        setTypeface(customFont);
    }
}