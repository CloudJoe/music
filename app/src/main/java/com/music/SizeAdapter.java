package com.music;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class SizeAdapter extends RecyclerView.Adapter<SizeAdapter.ViewHolder> {

    public ItemClickListener mItemClickListener;
    public List<Data> mdata;

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

    public SizeAdapter(Context context,List<Data> data) {
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
        Data data=mdata.get(position);
        holder.timbre.setText(data.g);
        holder.timbre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shareStr.length;
    }
}