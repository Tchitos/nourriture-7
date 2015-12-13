package com.android.nurriture.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.RecipeDetailActivity;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nuttriture.adapter.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/11.
 */
public class RecipeListFragment extends Fragment {

    private ListView listView;
    private List<RecipeInfo> recipeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View recipe = (View)inflater.inflate(R.layout.recipe_list, container,false);
        init(recipe,inflater);
        return recipe;
    }

    private void init(View view,LayoutInflater inflater)
    {
        listView = (ListView)view.findViewById(R.id.recipe_list_view);
        recipeList = new ArrayList<RecipeInfo>();
        final RecipeInfo recipeInfo = new RecipeInfo();
        recipeInfo.setName("Cucumber");
        recipeInfo.setType("type");
        recipeInfo.setImgpath("//");
        recipeList.add(recipeInfo);
        recipeList.add(recipeInfo);
        listView.setAdapter(new RecipeAdapter(recipeList, inflater));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("recipename",recipeInfo.getName());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
