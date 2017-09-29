package com.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;


public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    public ItemClickListener mItemClickListener;
    public List<SizeBean> mdata;

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public interface ItemClickListener {
        public void onItemClick(int pos);
    }

    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public final TextView timbre;
        public final TextView size;

        public ViewHolder(View view) {
            super(view);
            timbre = (TextView) view.findViewById(R.id.timbre);
            size = (TextView) view.findViewById(R.id.size);
        }


    }

    public SizeAdapter(Context context,List<SizeBean> data) {
        super();
        mContext = context;
        mdata=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checksize_itme, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        SizeBean sizeBean=mdata.get(position);
        double i=(sizeBean.getSize())/(1024*1024);
        DecimalFormat df = new DecimalFormat("#.00");
        holder.timbre.setText(sizeBean.getTimbre());
        if (i==0){
            holder.timbre.setEnabled(false);
            holder.size.setText("该音乐无此音质");
        }else {
            holder.size.setText(df.format(i)+"M");
        holder.timbre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(position);
                Log.i("点击","被点击");
            }
        });}}


    @Override
    public int getItemCount() {
        return mdata.size();
    }
}