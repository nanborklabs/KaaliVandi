package com.kaalivandi.UI;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by nandhu on 4/9/16.
 */
public class CEditText extends EditText {


    private Context context;
    private AttributeSet attrs;
    private int defStyle;

    public CEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public CEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        this.defStyle = defStyle;
        init();
    }

    private void init() {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/ios_light.ttf");
        this.setTypeface(font);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/ios_light.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
        tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ios_light.ttf");
        super.setTypeface(tf);
    }
}