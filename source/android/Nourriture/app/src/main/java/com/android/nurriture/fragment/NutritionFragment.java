package com.android.nurriture.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nourriture.nourriture.NutritionDetailActivity;
import com.android.nourriture.nourriture.R;
import com.android.nurriture.entity.NutritionInfo;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 */
public class NutritionFragment extends Fragment {

    private String[] nutrition = {"Nutrient","Mineral"};

    private String[] Nutrient = {"Vitamin A","Vitamin B1","Vitamin B2","Vitamin B3","Vitamin B4","Vitamin B5","Vitamin B6",
            "Vitamin C","Vitamin D","Vitamin E","Vitamin H","Vitamin K","Vitamin P","Vitamin B11","Vitamin B12"};

    private Context context;
    private int screenWidth;

    private MyGridView gv_nutrition;
    private MyGridView gv_mineral;
    private List<String> nutritionList;
    private List<String> mineralList;

    private List<NutritionInfo> nutritionInfoList;

    private MyGridViewAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.context = container.getContext();
        View nutritionView = (View)inflater.inflate(R.layout.layout_nutrition, container, false);

        initNutritionGV(nutritionView,inflater);

        //initMineralGV(nutritionView,inflater);

        return nutritionView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initNutritionGV(View view,LayoutInflater inflater)
    {
        nutritionInfoList = new ArrayList<NutritionInfo>();
        gv_nutrition = (MyGridView)view.findViewById(R.id.gv_nutrition);
        nutritionList = new ArrayList<String>();
        myAdapter = new MyGridViewAdapter(nutritionList, inflater);
        gv_nutrition.setAdapter(myAdapter);

        if(nutritionInfoList.size() == 0) {
            HttpUtil connectNet2 = new HttpUtil("/getNutritions", HttpMethod.GET) {

                @Override
                protected void getResult(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String statusCode = jsonObject.getString("statusCode");
                        String value = jsonObject.getString("value");

                        JSONArray jsonArray = new JSONArray(value);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jb = jsonArray.getJSONObject(i);
                            String name = jb.getString("name");
                            String effect = jb.getString("effects");
                            String dailyIntake = jb.getString("dailyIntake");
                            String supplementCycle = jb.getString("supplementCycle");


                            nutritionList.add(name);
                            JSONObject jo = new JSONObject(effect);
                            NutritionInfo nutritionInfo = new NutritionInfo();
                            nutritionInfo.setName(name);
                            nutritionInfo.setEffect(jo.getString("0"));
                            nutritionInfo.setDailyIntake(dailyIntake);
                            nutritionInfo.setSupplementCycle(supplementCycle);
                            nutritionInfoList.add(nutritionInfo);
                        }
                        myAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            connectNet2.execute();
        }


        gv_nutrition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(), "jinrudetail", Toast.LENGTH_SHORT).show();
                NutritionInfo nutritionInfo = nutritionInfoList.get(position);
                Intent intent = new Intent(getActivity(), NutritionDetailActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("nutrition",nutritionInfo);
                intent.putExtras(mBundle);
                startActivity(intent);

            }
        });
    }

    /*private void initMineralGV(View view,LayoutInflater inflater)
    {
        gv_mineral = (MyGridView)view.findViewById(R.id.gv_mineral);
        mineralList = new ArrayList<String>();
        mineralList.add("Vitamin A");
        mineralList.add("Vitamin B1");
        mineralList.add("Vitamin B2");
        mineralList.add("Vitamin B3");
        mineralList.add("Vitamin B4");
        mineralList.add("Vitamin B5");
        mineralList.add("Vitamin B6");
        mineralList.add("Vitamin C");
        mineralList.add("Vitamin D");
        mineralList.add("Vitamin E");
        mineralList.add("Vitamin H");
        mineralList.add("Vitamin K");
        mineralList.add("Vitamin P");
        mineralList.add("Vitamin B11");
        mineralList.add("Vitamin B12");
        gv_mineral.setAdapter(new MyGridViewAdapter(nutritionList, inflater));
        gv_mineral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;

                intent = new Intent(getActivity(), NutritionDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("NutritionName", "Vitamin A");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }*/

    private class MyGridViewAdapter extends BaseAdapter
    {
        List<String> stringList;
        LayoutInflater inflater;
        public MyGridViewAdapter(List<String> stringList,LayoutInflater inflater) {
            this.stringList = stringList;
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = (View)inflater.inflate(R.layout.gridview_nutrition,parent,false);
            TextView recipeType = (TextView)convertView.findViewById(R.id.recipe_type);
            recipeType.setText(stringList.get(position));
            return convertView;
        }
    }


}
