package com.itechvision.ecrobo.pickman.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.itechvision.ecrobo.pickman.Activity.ScannerBindActivity;

/**
 * Created by Komal on 17-Oct-18.
 */

public class InputKeyButton extends Button {

    protected int keyCode = 0;

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public InputKeyButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initializeEvent(context);
    }

    public InputKeyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initializeEvent(context);
    }

    public InputKeyButton(Context context) {
        super(context);
        this.initializeEvent(context);
    }

    protected boolean initializeEvent(final Context context) {
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                ((ScannerBindActivity) context).keyput(b.getText().toString());
            }
        });
        return true;
    }

}

