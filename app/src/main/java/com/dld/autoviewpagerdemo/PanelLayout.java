package com.dld.autoviewpagerdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by song on 2016/11/14.
 */

public class PanelLayout extends LinearLayout implements IPanel{

    public PanelLayout(Context context) {
        super(context);
    }

    public PanelLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PanelLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void refreshHeight(int height){
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
    }



}
