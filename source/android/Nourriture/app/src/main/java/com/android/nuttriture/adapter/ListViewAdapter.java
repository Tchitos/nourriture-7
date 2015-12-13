package com.android.nuttriture.adapter;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.fragment.IngredientFragment;

/**
 * Created by Administrator on 2015/12/11.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context context;
    private String[] strings;
    public static int mPosition;

    public ListViewAdapter(Context context, String[] strings){
        this.context = context;
        this.strings = strings;
    }

    @Override
    public int getCount(){
        return strings.length;
    }

    @Override
    public Object getItem(int position){
        return strings[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView,ViewGroup parent){
        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item,parent,false);
        TextView tv = (TextView) convertView.findViewById(R.id.list_item);
        mPosition = position;
        tv.setText(strings[position]);
        if (position == IngredientFragment.mPosition) {
            convertView.setBackgroundResource(R.drawable.tongcheng_all_bg01);
        } else {
            convertView.setBackgroundColor(Color.parseColor("#f4f4f4"));
        }
        return convertView;
    }
}