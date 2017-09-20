package com.anandniketanbhadaj.skool360.skool360.Activities;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by admsandroid on 9/7/2017.
 */

public class Spanable  extends ClickableSpan {

    private boolean isUnderline = true;

    /**
     * Constructor
     */
    public Spanable(boolean isUnderline) {
        this.isUnderline = isUnderline;
    }

    @Override
    public void updateDrawState(TextPaint ds) {

        ds.setUnderlineText(isUnderline);
        ds.setColor(Color.parseColor("#1791d8"));

    }

    @Override
    public void onClick(View widget) {

    }
}
