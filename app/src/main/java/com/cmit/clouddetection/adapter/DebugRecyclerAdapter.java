package com.cmit.clouddetection.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cmit.clouddetection.R;

import java.util.List;

/**
 * Created by pact on 2018/10/10.
 */

public class DebugRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<String> mList;

    public DebugRecyclerAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_logcat, parent, false);
        RecyclerHolder holder = new RecyclerHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RecyclerHolder) holder).text.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView text;

        public RecyclerHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_logcat);
        }
    }
}
