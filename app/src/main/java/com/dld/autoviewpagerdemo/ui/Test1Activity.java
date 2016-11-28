package com.dld.autoviewpagerdemo.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dld.autoviewpagerdemo.PanelLayout;
import com.dld.autoviewpagerdemo.R;
import com.dld.autoviewpagerdemo.SoftSwitchListener;
import com.dld.autoviewpagerdemo.adapter.ItemAdapter;
import com.dld.autoviewpagerdemo.utils.KeyboardSwitchUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class Test1Activity extends AppCompatActivity {

    private static final String TAG = "Test1Activity";

    @BindView(R.id.panelLayout)
    PanelLayout mPanelLayout;
    @BindView(R.id.msg_et)
    EditText mMsgEt;
    @BindView(R.id.footView_ll)
    LinearLayout mFootViewLl;
    @BindView(R.id.rootView_ll)
    LinearLayout mRootViewLl;
    @BindView(R.id.rotate_header_list_view)
    ListView mListRv;
    @BindView(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;

    private List<String> mItemList;
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        translucentStatus();
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);
        init();
        setListener();
    }


    private void init() {

        mItemList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mItemList.add(String.format(Locale.ENGLISH, "item:%d", i));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItemList);
        mListRv.setAdapter(adapter);

        KeyboardSwitchUtils.attach(this, mPanelLayout, mMsgEt, new SoftSwitchListener() {
            @Override
            public void switchTo(boolean isShowing) {


                Log.d(TAG, String.format("isShowing:%b", isShowing));
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void translucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void setListener() {

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrame.autoRefresh();
            }
        }, 100);

//        mMsgEt.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if(mPanelLayout.getVisibility() != View.VISIBLE)
//                        mPanelLayout.setVisibility(View.VISIBLE);
//                    return true;
//                }
//                return false;
//            }
//        });


//        mListRv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_UP){
//                    KeyboardSwitchUtils.hideKeyboard(mPanelLayout, mMsgEt);
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    private void updateData() {

        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {

                List<String> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    list.add(String.format(Locale.ENGLISH, "item:%d", i));
                }
                mItemList.addAll(list);
                mPtrFrame.refreshComplete();
                adapter.notifyDataSetChanged();
            }
        }, 0);

    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN ||
                event.getKeyCode() == KeyEvent.KEYCODE_BACK){
           KeyboardSwitchUtils.hideKeyboard(mPanelLayout, mMsgEt);
        }
        return super.dispatchKeyEvent(event);
    }




}
