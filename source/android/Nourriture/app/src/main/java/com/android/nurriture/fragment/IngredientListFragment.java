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

import com.android.nourriture.nourriture.IngredientDetailActivity;
import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.IngredientInfo;
import com.android.nurriture.entity.RecipeInfo;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;
import com.android.nuttriture.adapter.IngredientAdapter;
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
public class IngredientListFragment extends Fragment {

    private ListView listView;
    private List<IngredientInfo> ingredientInfoList;
    private String search_type;
    private String search_text;
    private IngredientAdapter ingredientAdapter;
    private TextView no_record;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View ingredient = (View)inflater.inflate(R.layout.ingredient_list, container,false);
        init(ingredient,inflater);
        connectServer();
        return ingredient;
    }

    private void connectServer()
    {
        searchIngredientsBySearch();
    }

    private void searchIngredientsBySearch()
    {
        Map<String,String> map = new HashMap<String,String>();
        map.put("search",search_text);
        HttpUtil connect = new HttpUtil("/getIngredientsBySearch", HttpMethod.POST,map) {
            @Override
            protected void getResult(String result) {
                try{
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    if(statusCode.equals("201"))
                    {
                        no_record.setVisibility(View.VISIBLE);
                    }else if(statusCode.equals("401"))
                    {
                        no_record.setVisibility(View.VISIBLE);
                    }else{


                    String value = jsonObject.getString("value");

                    JSONArray ingredients = new JSONArray(value);
                    if(ingredients.length()==0)
                    {
                        no_record.setVisibility(View.VISIBLE);
                    }
                    for(int i=0;i<ingredients.length();i++)
                    {
                        //Toast.makeText(getActivity(), "haveRecord:"+search_text, Toast.LENGTH_SHORT).show();
                        JSONObject ingredient = ingredients.getJSONObject(i);
                        IngredientInfo ingredientInfo = new IngredientInfo();
                        ingredientInfo.setIngreName(ingredient.getString("name"));
                        ingredientInfoList.add(ingredientInfo);
                    }
                    ingredientAdapter.notifyDataSetChanged();
                   // Toast.makeText(getActivity(), "searchRecipeByIngredient:"+search_text, Toast.LENGTH_SHORT).show();
                    }
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

        listView = (ListView)view.findViewById(R.id.ingredient_list_view);
        ingredientInfoList = new ArrayList<IngredientInfo>();
        ingredientAdapter = new IngredientAdapter(ingredientInfoList, inflater);
        no_record = (TextView)view.findViewById(R.id.no_record);
        /*IngredientInfo ingredientInfo = new IngredientInfo();
        ingredientInfo.setIngreName("Beef");
        ingredientInfoList.add(ingredientInfo);
        ingredientInfoList.add(ingredientInfo);*/
        listView.setAdapter(ingredientAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), IngredientDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ingredientName",ingredientInfoList.get(position).getIngreName());
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
