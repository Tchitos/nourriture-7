package com.android.nuttriture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.MutualInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class MutualAdapter extends BaseAdapter {
    List<MutualInfo> mutualInfoList;
    LayoutInflater inflater;

    public MutualAdapter(List<MutualInfo> mutualInfoList, LayoutInflater inflater) {
        this.mutualInfoList = mutualInfoList;
        this.inflater = inflater;
    }

    public MutualAdapter(List<MutualInfo> mutualInfoList) {
        this.mutualInfoList = mutualInfoList;
    }

    @Override
    public int getCount() {
        return mutualInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mutualInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = (View)inflater.inflate(R.layout.mutulesuit_list_item,parent,false);
        return convertView;
    }
}
