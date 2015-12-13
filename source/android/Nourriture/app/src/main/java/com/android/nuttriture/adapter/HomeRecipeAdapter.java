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
public class HomeRecipeAdapter extends BaseAdapter {
    List<RecipeInfo> recipeInfoList;
    LayoutInflater inflater;

    public HomeRecipeAdapter(List<RecipeInfo> recipeInfoList, LayoutInflater inflater) {
        this.recipeInfoList = recipeInfoList;
        this.inflater = inflater;
    }

    public HomeRecipeAdapter(List<RecipeInfo> recipeInfoList) {
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
        convertView = (View)inflater.inflate(R.layout.listview_recipe_item,parent,false);
        return convertView;
    }
}
