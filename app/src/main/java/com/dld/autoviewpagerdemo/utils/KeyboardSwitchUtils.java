package com.dld.autoviewpagerdemo.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import com.dld.autoviewpagerdemo.IPanel;
import com.dld.autoviewpagerdemo.SoftSwitchListener;

/**
 * Created by song on 2016/11/14.
 */

public class KeyboardSwitchUtils {


    private static final String TAG = "KeyboardSwitchUtils";

    public static void attach(Activity activity, final IPanel iPanel, final View focusView, SoftSwitchListener listener){


        final View contentView = activity.findViewById(android.R.id.content);
        int statusHeight = StatusBarUtils.getStatusBarHeight(activity);

        boolean isAllowTranslucentStatus = StatusBarUtils.isAllowTranslucentStatus();

        contentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new OnKeyboardGlobalLayoutListener(contentView, focusView, iPanel, statusHeight, isAllowTranslucentStatus, listener));



        focusView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    KeyboardUtils.showKeyboard(focusView);
                    return true;
                }
                return false;
            }
        });

    }

    private static class OnKeyboardGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        private View contentView;
        private IPanel iPanel;
        private int statusHeight;
        private SoftSwitchListener softSwitchListener;
        private View focusView;
        private boolean isAllowTranslucentStatus;

        private int previousHeight;
        private boolean isShowing;
        private boolean previousSoftKeyboard;
        private View iPanelView;

        public OnKeyboardGlobalLayoutListener(View contentView, View focusView, IPanel iPanel, int statusHeight,
                                              boolean isAllowTranslucentStatus, SoftSwitchListener listener) {
            this.contentView = contentView;
            this.iPanel = iPanel;
            this.statusHeight = statusHeight;
            this.softSwitchListener = listener;
            this.focusView = focusView;
            iPanelView = (View) iPanel;
            this.isAllowTranslucentStatus = isAllowTranslucentStatus;
        }

        @Override
        public void onGlobalLayout() {

            Rect rect = new Rect();
            View rootView = (View) contentView.getParent();
            rootView.getWindowVisibleDisplayFrame(rect);
            int rootViewHeight = rect.bottom - rect.top + statusHeight;
            Log.d(TAG, String.format("rootViewHeight:%d", rootViewHeight));

//            int height = rootView.getHeight();

            int maxHeight = 0;
            if(previousHeight == 0){
                previousHeight = rootViewHeight;
                maxHeight = Math.max(previousHeight, maxHeight);
            }else {
                maxHeight = Math.max(previousHeight, maxHeight);
                int softInputHeight = Math.abs(rootViewHeight - previousHeight);
                if(softInputHeight != 0)
                    iPanel.refreshHeight(softInputHeight);
            }
            isShowing = rootViewHeight < maxHeight;

            if(previousSoftKeyboard != isShowing){
                switchTo(isShowing);
                previousSoftKeyboard = isShowing;

                if(!isAllowTranslucentStatus){
                    iPanelView.setVisibility(View.GONE);
                }else {
                    int visibility = previousSoftKeyboard?View.VISIBLE:View.GONE;
                    iPanelView.setVisibility(visibility);
                }

            }

            if(isShowing){
                focusView.requestFocus();
            }
        }

        void switchTo(boolean isShowing){
            if(softSwitchListener != null)
                softSwitchListener.switchTo(isShowing);
        }
    }

    public static void hideKeyboard(View iPanel, View focusView){
        KeyboardUtils.hideKeyboard(focusView);
        if(!StatusBarUtils.isAllowTranslucentStatus()){
            return;
        }
        if(iPanel.getVisibility() == View.GONE)
            return;
        iPanel.setVisibility(View.GONE);

    }



}
