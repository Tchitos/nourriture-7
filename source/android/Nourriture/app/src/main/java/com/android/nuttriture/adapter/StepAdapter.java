package com.android.nuttriture.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.MutualInfo;
import com.android.nurriture.entity.StepInfo;
import com.android.nurriture.fragment.IngredientFragment;

import java.util.List;

/**
 * Created by Administrator on 2015/12/13.
 */
public class StepAdapter extends BaseAdapter {
    List<StepInfo> StepInfoList;
    LayoutInflater inflater;

    public StepAdapter(List<StepInfo> StepInfoList, LayoutInflater inflater) {
        this.StepInfoList = StepInfoList;
        this.inflater = inflater;
    }

    public StepAdapter(List<StepInfo> StepInfoList) {
        this.StepInfoList = StepInfoList;
    }

    @Override
    public int getCount() {
        return StepInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return StepInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = (View)inflater.inflate(R.layout.step_list_item,parent,false);
        TextView step_number = (TextView) convertView.findViewById(R.id.step_number);
        step_number.setText(StepInfoList.get(position).getNumber());
        Log.v("number:",StepInfoList.get(position).getNumber());
        TextView des = (TextView)convertView.findViewById(R.id.setp_desc);
        des.setText(StepInfoList.get(position).getDesc());
        Log.v("desc:", StepInfoList.get(position).getDesc());
        ImageView img = (ImageView)convertView.findViewById(R.id.step_img);
        img.setImageResource(R.mipmap.home_recipe);
        return convertView;
    }
//    private String number[];
//    private String desc[];
//    private String img[];
//    private Context context;
//
//    public StepAdapter(Context context,String[] number,String[] desc,String[] img){
//        this.number = number;
//        this.desc = desc;
//        this.img = img;
//        this.context = context;
//    }
//    @Override
//    public Object getItem(int position) {
//        return number[position];
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public int getCount() {
//        return number.length;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        convertView = LayoutInflater.from(context).inflate(R.layout.step_list_item,parent,false);
//        TextView step_number = (TextView) convertView.findViewById(R.id.step_number);
//        step_number.setText(number[position]);
//        return convertView;
//    }
}
