package com.kaalivandi.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Nandha on 9/22/2016.
 */

public class IosLight extends TextView {
    public IosLight(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public IosLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public IosLight(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }
    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/ios_light.ttf", context);
        setTypeface(customFont);
    }
}
