package com.android.nuttriture.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.IngredientInfo;
import com.android.nurriture.entity.RecipeInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/12/10.
 */
public class IngredientAdapter extends BaseAdapter {
    List<IngredientInfo> ingredientInfoList;
    LayoutInflater inflater;

    public IngredientAdapter(List<IngredientInfo> ingredientInfoList, LayoutInflater inflater) {
        this.ingredientInfoList = ingredientInfoList;
        this.inflater = inflater;
    }

    public IngredientAdapter(List<IngredientInfo> ingredientInfoList) {
        this.ingredientInfoList = ingredientInfoList;
    }

    @Override
    public int getCount() {
        return ingredientInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredientInfoList.get(position);
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
            convertView = (View)inflater.inflate(R.layout.ingredient_list_item, parent, false);
            holder.ingredient_name = (TextView) convertView.findViewById(R.id.ingredient_name);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder) convertView.getTag();
        IngredientInfo ingredientInfo = (IngredientInfo)getItem(position);
        if (ingredientInfo != null && ingredientInfoList.size() > 0) {
            holder.ingredient_name.setText(String.valueOf(ingredientInfo.getIngreName()));
        }
        return convertView;
    }

    public class ViewHolder {
        TextView ingredient_name;
    }
}
