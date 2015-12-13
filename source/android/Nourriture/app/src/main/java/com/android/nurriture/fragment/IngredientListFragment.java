package com.android.nurriture.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.IngredientInfo;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nuttriture.adapter.IngredientAdapter;
import com.android.nuttriture.adapter.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public class IngredientListFragment extends Fragment {

    private ListView listView;
    private List<IngredientInfo> ingredientInfoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View ingredient = (View)inflater.inflate(R.layout.ingredient_list, container,false);
        init(ingredient,inflater);
        return ingredient;
    }

    private void init(View view,LayoutInflater inflater)
    {
        listView = (ListView)view.findViewById(R.id.ingredient_list_view);
        ingredientInfoList = new ArrayList<IngredientInfo>();
        IngredientInfo ingredientInfo = new IngredientInfo();
        ingredientInfo.setIngreName("Beef");
        ingredientInfoList.add(ingredientInfo);
        ingredientInfoList.add(ingredientInfo);
        listView.setAdapter(new IngredientAdapter(ingredientInfoList,inflater));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
