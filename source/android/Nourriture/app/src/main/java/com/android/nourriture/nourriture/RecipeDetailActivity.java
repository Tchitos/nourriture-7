package com.android.nourriture.nourriture;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import com.android.nurriture.entity.RecipeInfo;
import com.android.nurriture.entity.StepInfo;
import com.android.nurriture.fragment.MyListView;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;
import com.android.nuttriture.adapter.StepAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/13.
 */
public class RecipeDetailActivity extends Activity{

    private String[] materials = {"Cabbage","Black fungus","Dry pepper","Salt",
            "Chicken powder","Vinegar","Ginger","Garlic","Peanut oil"};
    private String[] much = {"250g","10g","2","4g",
            "2g","1spoon","3slice","3","5g"};
    private String number[]={"1","2","3","4","5","6"};
    private String desc="Pomelo spicy chicken wings can pour wine as a kind of snack";
    private String img;

    private ImageView back_img;
    private TextView nameOfrecipe,author_name,recipe_intro,recipe_tips;
    private int screenWidth;
    private String recipename,username,image,tips;
    private List<StepInfo> stepList;
    private StepAdapter stepAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);
        DisplayMetrics  dm = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        getBundle();
        init();
        initTable();
        initList();

        getData();
    }
    private void getData(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", recipename);
        HttpUtil connectNet = new HttpUtil("/getRecipeByName", HttpMethod.POST, map) {
            @Override
            protected void getResult(String result) {
                Toast.makeText(getApplicationContext(), "Connect", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "Connect Server API success!=" + result, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    if(statusCode == "401"){
                        Toast.makeText(getApplicationContext(), "Failed" + result, Toast.LENGTH_SHORT).show();
                    }else if(statusCode == "201"){

                        Toast.makeText(getApplicationContext(), "Failed" + result, Toast.LENGTH_SHORT).show();
                    }else{
                        String value = jsonObject.getString("value");
                      Log.v("wzzvalue:", value);
//                    Toast.makeText(getApplicationContext(), "statusCode="+statusCode+" value:"+value,
//                            Toast.LENGTH_SHORT).show();
                        if(value!="" && value!=null && !value.isEmpty()){
                            try {
                                JSONObject recipe = new JSONObject(value);
                                String name = recipe.getString("name");
                                image = recipe.getString("image");
                                Log.v("image:", image);
                                desc = recipe.getString("description");
                                Log.v("desc:", desc);
                                tips = recipe.getString("tips");
                                Log.v("tips:", tips);
                                /*JSONArray users = new JSONArray(recipe.getString("users"));
                                Log.v("wzzusername:", users.toString());
                                JSONObject user = users.getJSONObject(0);
                                username = user.getString("username");
                                Log.v("username:", username);*/

                                nameOfrecipe.setText(recipename);
                                //author_name = (TextView)findViewById(R.id.author_name);
                                //author_name.setText(username);

                               recipe_intro.setText(desc);

                                recipe_tips.setText(tips);
                                JSONArray stpes = new JSONArray(recipe.getString("steps"));
                                Toast.makeText(getApplicationContext(), "Connect step", Toast.LENGTH_SHORT).show();
                                Log.v("wzzstep:",recipe.getString("steps"));
                                if(stpes.length()>0){
                                    for(int i = 0;i<stpes.length();i++){
                                        Log.v("stepvalue:",recipe.getString("steps"));
                                        Log.v("number length:",i+"");
                                        StepInfo stepInfo = new StepInfo();
                                        stepInfo.setNumber(number[i]);
                                        stepInfo.setDesc(desc);
                                        stepInfo.setImg(img);
                                        stepList.add(stepInfo);
                                    }
                                    stepAdapter.notifyDataSetChanged();
                                }

                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        connectNet.execute();
    }

    private void init()
    {
        stepList = new ArrayList<StepInfo>();
        stepAdapter = new StepAdapter(stepList,this.getLayoutInflater());
        nameOfrecipe = (TextView)findViewById(R.id.recipe_name);
        recipe_intro = (TextView)findViewById(R.id.recipe_intro);
        recipe_tips = (TextView)findViewById(R.id.recipe_tips);
        back_img = (ImageView)findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTable(){

        TableLayout tableLayout = (TableLayout)findViewById(R.id.recipe_materials);
        TableLayout.LayoutParams tablelayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams tablerowlayout =
                new TableRow.LayoutParams((screenWidth-40)/2, TableRow.LayoutParams.WRAP_CONTENT);
        tablerowlayout.setMargins(0, 0, 0, 2);

        for(int j = 0; j < materials.length; j++){
            TableRow tablerow = new TableRow(this);
            tablerow.setLayoutParams(tablelayout);
            tablerow.setGravity(Gravity.LEFT);
            tableLayout.addView(tablerow);
            TextView material = new TextView(this);
            material.setGravity(Gravity.LEFT);
            material.setPadding(10, 10, 10, 10);
            material.setText(materials[j]);
            material.setTextColor(Color.BLACK);
            material.setBackgroundColor(Color.WHITE);
            material.setLayoutParams(tablerowlayout);
            TextView  weight = new TextView(this);
            weight.setGravity(Gravity.LEFT);
            weight.setPadding(10, 10, 10, 10);
            weight.setText(much[j]);
            weight.setLayoutParams(tablerowlayout);
            weight.setTextColor(Color.BLACK);
            weight.setBackgroundColor(Color.WHITE);
            tablerow.addView(material);
            tablerow.addView(weight);
        }


    }

    private void initList(){
        Log.v("initList:","enter");
        MyListView listView = (MyListView)findViewById(R.id.recipe_steps);
//        List<StepInfo> stepList = new ArrayList<StepInfo>();
//        StepInfo stepInfo;
//        for(int i = 0; i < number.length; i++){
//            Log.v("number length:",i+"");
//            stepInfo = new StepInfo();
//            stepInfo.setNumber(number[i]);
//            stepInfo.setDesc(desc);
//            stepInfo.setImg(img);
//            stepList.add(stepInfo);
//        }
//        StepAdapter stepAdapter = new StepAdapter(stepList,this.getLayoutInflater());
        listView.setAdapter(stepAdapter);

        //fixListViewHeight(listView, stepAdapter);
    }

    public void fixListViewHeight(ListView listView,StepAdapter stepAdapter) {
        // ListViewû

        int totalHeight = 0;
        for (int index = 0, len = stepAdapter.getCount(); index < len; index++) {
            View listViewItem = stepAdapter.getView(index , null, listView);

            listViewItem.measure(0, 0);

            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()
        // params.height ListView
        params.height = totalHeight+ (listView.getDividerHeight() * (stepAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void getBundle() {
        Bundle bundle = this.getIntent().getExtras();
        recipename = bundle.getString("recipename");
    }
}

