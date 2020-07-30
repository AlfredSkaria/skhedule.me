package com.alfred.karela.schedule.customfonts;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Bineesh P Babu on 16-12-2016.
 */

public class FontCheckBox extends CheckBox {
    public FontCheckBox(Context context) {
        super(context);
        init();
    }
    public FontCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public FontCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Chinrg.ttf");
            setTypeface(tf);
        }
    }
}
