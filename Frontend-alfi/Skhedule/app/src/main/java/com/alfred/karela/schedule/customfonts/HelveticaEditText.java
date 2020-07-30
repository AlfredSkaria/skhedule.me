package com.alfred.karela.schedule.customfonts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Bineesh P Babu on 27-08-2016.
 */
public class HelveticaEditText extends EditText {

    public HelveticaEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public HelveticaEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HelveticaEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Lato-Regular.ttf");
            setTypeface(tf);
        }
    }

}