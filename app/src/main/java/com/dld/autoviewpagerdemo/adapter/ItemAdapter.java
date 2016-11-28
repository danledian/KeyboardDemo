package com.dld.autoviewpagerdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dld.autoviewpagerdemo.R;
import com.dld.autoviewpagerdemo.ui.Test1Activity;

import java.util.List;

/**
 * Created by song on 2016/11/15.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context mContext;
    private List<String> mItemList;

    public ItemAdapter(Context mContext, List<String> mItemList) {
        this.mContext = mContext;
        this.mItemList = mItemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mItemTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemTv = (TextView) itemView.findViewById(R.id.item_tv);
        }

        public void bindData(String text) {
            mItemTv.setText(String.format("%s", text));
        }
    }
}
