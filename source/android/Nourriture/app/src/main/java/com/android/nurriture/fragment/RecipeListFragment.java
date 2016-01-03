package com.android.nurriture.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.RecipeDetailActivity;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;
import com.android.nuttriture.adapter.RecipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/11.
 */
public class RecipeListFragment extends Fragment {

    private ListView listView;
    private List<RecipeInfo> recipeList;
    private String search_type;
    private String search_text;
    private RecipeAdapter recipeAdapter;

    private TextView no_record;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View recipe = (View)inflater.inflate(R.layout.recipe_list, container,false);
        init(recipe,inflater);
        connectServer();
        return recipe;
    }

    private void connectServer()
    {
        if(search_type.equals("ingredient_type"))
        {
            searchRecipeByIngredient();
        }else if(search_type.equals("recipe_classification")){
            searchRecipeBySubType();
        }else{
            //Toast.makeText(getActivity(), "both:"+search_type, Toast.LENGTH_SHORT).show();
            searchRecipeByIngredient();
            searchRecipeBySubType();
        }
    }

    private void searchRecipeBySubType()
    {
        Map<String,String> map = new HashMap<String,String>();
        map.put("search",search_text);
        HttpUtil connect = new HttpUtil("/getRecipesBySubtype", HttpMethod.POST,map) {
            @Override
            protected void getResult(String result) {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    String value = jsonObject.getString("value");

                    JSONArray recipes = new JSONArray(value);
                    if(recipes.length()==0)
                    {
                        no_record.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<recipes.length();i++)
                    {
                        JSONObject recipe = recipes.getJSONObject(i);
                        RecipeInfo recipeInfo = new RecipeInfo();
                        recipeInfo.setName(recipe.getString("name"));
                        recipeInfo.setDescription(recipe.getString("description"));
                        recipeInfo.setType("type");
                        recipeInfo.setImgpath("//");
                        recipeList.add(recipeInfo);
                    }
                    recipeAdapter.notifyDataSetChanged();
                    //Toast.makeText(getActivity(), "searchRecipeBySubType:"+search_text, Toast.LENGTH_SHORT).show();
                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        };
        connect.execute();
    }

    private void searchRecipeByIngredient()
    {
        Map<String,String> map = new HashMap<String,String>();
        map.put("search",search_text);
        HttpUtil connect = new HttpUtil("/getRecipesByIngredient", HttpMethod.POST,map) {
            @Override
            protected void getResult(String result) {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    String value = jsonObject.getString("value");

                    JSONArray recipes = new JSONArray(value);
                    if(recipes.length()==0)
                    {
                        no_record.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<recipes.length();i++)
                    {
                        JSONObject recipe = recipes.getJSONObject(i);
                        RecipeInfo recipeInfo = new RecipeInfo();
                        recipeInfo.setName(recipe.getString("name"));
                        recipeInfo.setDescription(recipe.getString("description"));
                        recipeInfo.setType("type");
                        recipeInfo.setImgpath("//");
                        recipeList.add(recipeInfo);
                    }
                    recipeAdapter.notifyDataSetChanged();

                }catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
        };
        connect.execute();
    }

    private void init(View view,LayoutInflater inflater)
    {
        Bundle bundle = getArguments();
        search_type = bundle.getString("search_type");
        search_text = bundle.getString("search_text");
       // Toast.makeText(getActivity(), "huodele searchtext:"+search_text, Toast.LENGTH_SHORT).show();
        listView = (ListView)view.findViewById(R.id.recipe_list_view);
        no_record = (TextView)view.findViewById(R.id.no_record);
        recipeList = new ArrayList<RecipeInfo>();
        recipeAdapter = new RecipeAdapter(recipeList,inflater);
        /*final RecipeInfo recipeInfo = new RecipeInfo();
        recipeInfo.setName("Cucumber");
        recipeInfo.setType("type");
        recipeInfo.setImgpath("//");
        recipeList.add(recipeInfo);
        recipeList.add(recipeInfo);*/
        listView.setAdapter(recipeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("recipename",recipeList.get(position).getName());
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
