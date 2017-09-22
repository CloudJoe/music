package com.music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Data> {

    private int id;


    public MyAdapter(Context context, int resource, List<Data> objects) {
        super(context, resource, objects);
        this.id = resource;
    }
    public View getView(int position,View convertView, ViewGroup parent){
        Data data= getItem(position);
        View view = null;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(getContext()).inflate(id,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.songname=(TextView)view.findViewById(R.id.songname);
            viewHolder.singer=(TextView)view.findViewById(R.id.singer);
//            viewHolder.coursename=(TextView)view.findViewById(R.id.coursename);
//            viewHolder.type=(TextView)view.findViewById(R.id.type);
//            viewHolder.day=(TextView)view.findViewById(R.id.day);

            view.setTag(viewHolder);
        }
        else {
            view =convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.songname.setText(data.getName());
        viewHolder.singer.setText(data.getSinger());
//        viewHolder.type.setText(date.getAbsenceType());
//        viewHolder.coursename.setText(date.getCourseName());
//        viewHolder.day.setText(date.getDay());

        return view;
    }

    private class ViewHolder {
        TextView songname;
        TextView singer;
//        TextView coursename;
//        TextView day;
//        TextView type;

    }
}