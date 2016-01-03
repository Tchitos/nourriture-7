package com.android.nuttriture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.RecipeInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class RecipeAdapter extends BaseAdapter {
    List<RecipeInfo> recipeInfoList;
    LayoutInflater inflater;

    public RecipeAdapter(List<RecipeInfo> recipeInfoList, LayoutInflater inflater) {
        this.recipeInfoList = recipeInfoList;
        this.inflater = inflater;
    }

    public RecipeAdapter(List<RecipeInfo> recipeInfoList) {
        this.recipeInfoList = recipeInfoList;
    }

    @Override
    public int getCount() {
        return recipeInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = (View) inflater.inflate(R.layout.recipe_list_item, parent, false);
            holder.recipe_name = (TextView) convertView.findViewById(R.id.recipe_name);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();
        RecipeInfo recipeInfo = (RecipeInfo)getItem(position);
        if (recipeInfoList != null && recipeInfoList.size() > 0) {
            holder.recipe_name.setText(String.valueOf(recipeInfo.getName()));
            holder.description.setText(recipeInfo.getDescription());
        }
        return convertView;
    }

    public class ViewHolder {
        TextView recipe_name;
        TextView description;
    }
}
