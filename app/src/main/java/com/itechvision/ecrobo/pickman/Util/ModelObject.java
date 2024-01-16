package com.itechvision.ecrobo.pickman.Util;

import com.itechvision.ecrobo.pickman.R;

/**
 * Created by lenovo on 11/20/2018.
 */

public enum ModelObject {

    BLUE(1, R.layout.number_keyboard),
    RED(2, R.layout.number_buttons);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
