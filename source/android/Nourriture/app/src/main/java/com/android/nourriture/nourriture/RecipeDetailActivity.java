package com.android.nourriture.nourriture;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.nurriture.entity.IngredientInfo;
import com.android.nurriture.entity.StepInfo;
import com.android.nurriture.fragment.MyListView;
import com.android.nurriture.util.Config;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;
import com.android.nurriture.util.SyncImageLoader;
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

    private String desc="";
    private ImageView back_img,recipe_img;
    private TextView nameOfrecipe,recipe_intro,recipe_tips;
    private int screenWidth;
    private String recipename,image,tips;
    private List<StepInfo> stepList;
    private List<IngredientInfo> ingredientInfoList;
    private StepAdapter stepAdapter;
    private SyncImageLoader syncImageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);
        DisplayMetrics  dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;

        init();
<<<<<<< HEAD
        initList();
=======
        initTable();
        initList();

>>>>>>> origin/master
        getData();
    }
    private void getData(){
        getBundle();
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", recipename);
        HttpUtil connectNet = new HttpUtil("/getRecipeByName", HttpMethod.POST, map) {
            @Override
            protected void getResult(String result) {
<<<<<<< HEAD

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    if (statusCode == "401") {

                    } else if (statusCode == "201") {

                    } else {
                        String value = jsonObject.optString("value");
                        Log.v("value:", value);
                        if (value != "" && value != null && !value.isEmpty()) {
=======
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
>>>>>>> origin/master
                            try {
                                JSONObject recipe = new JSONObject(value);
                                String name = recipe.optString("name");
                                image = recipe.optString("image");
                                Log.v("image:", recipe.optString("image"));
                                loadImage();
                                desc = recipe.optString("description");
                                Log.v("desc:", desc);
                                tips = recipe.optString("tips");
                                Log.v("tips:", tips);
<<<<<<< HEAD

                                nameOfrecipe.setText(name);
                                recipe_intro.setText(desc);
=======
                                /*JSONArray users = new JSONArray(recipe.getString("users"));
                                Log.v("wzzusername:", users.toString());
                                JSONObject user = users.getJSONObject(0);
                                username = user.getString("username");
                                Log.v("username:", username);*/

                                nameOfrecipe.setText(recipename);
                                //author_name = (TextView)findViewById(R.id.author_name);
                                //author_name.setText(username);

                               recipe_intro.setText(desc);

>>>>>>> origin/master
                                recipe_tips.setText(tips);

                                JSONArray stpes = new JSONArray(recipe.getString("steps"));
<<<<<<< HEAD
                                Log.v("stepvalue:", stpes.toString());
                                if (stpes.length() > 0) {
=======
                                Toast.makeText(getApplicationContext(), "Connect step", Toast.LENGTH_SHORT).show();
                                Log.v("wzzstep:",recipe.getString("steps"));
                                if(stpes.length()>0){
>>>>>>> origin/master
                                    for(int i = 0;i<stpes.length();i++){
                                        Log.v("stpes.getJSONObject:",stpes.getJSONObject(i).toString());
                                        JSONObject step = stpes.getJSONObject(i);
                                        StepInfo stepInfo = new StepInfo();
                                        stepInfo.setNumber(i + "");
                                        stepInfo.setDesc(step.optString("text"));

                                        stepInfo.setImg(step.optString("image"));

                                        stepList.add(stepInfo);
                                    }
                                    stepAdapter.notifyDataSetChanged();
                                }

                                JSONArray recipeIngredients = new JSONArray(recipe.getString("recipeIngredients"));
                                Log.v("recipeIngredients:", recipe.getString("recipeIngredients"));
                                if (recipeIngredients.length() > 0) {
                                    ingredientInfoList = new ArrayList<IngredientInfo>();
                                    for (int i = 0; i < recipeIngredients.length(); i++) {

                                        JSONObject ingredient = recipeIngredients.getJSONObject(i);
                                        Boolean mandatory = ingredient.getBoolean("mandatory");
                                        if(mandatory && ingredient.optString("ingredient")!="" && !ingredient.optString("ingredient").equals("")){
                                            JSONObject ingrentdientname = new JSONObject(ingredient.optString("ingredient"));
                                            IngredientInfo ingre = new IngredientInfo();
                                            ingre.setIngreName(ingrentdientname.optString("name"));
                                            ingre.setQuantity(ingredient.optString("quantity"));
                                            ingredientInfoList.add(ingre);
                                        }

                                    }
                                    initTable();
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
        recipe_img = (ImageView)findViewById(R.id.recipe_img);
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

        for(int j = 0; j < ingredientInfoList.size(); j++){
            TableRow tablerow = new TableRow(this);
            tablerow.setLayoutParams(tablelayout);
            tablerow.setGravity(Gravity.LEFT);
            tableLayout.addView(tablerow);
            IngredientInfo ingretablerow = ingredientInfoList.get(j);
            TextView material = new TextView(this);
            material.setGravity(Gravity.LEFT);
            material.setPadding(10, 10, 10, 10);
            material.setText(ingretablerow.getIngreName());
            material.setTextColor(Color.BLACK);
            material.setBackgroundColor(Color.WHITE);
            material.setLayoutParams(tablerowlayout);
            TextView  weight = new TextView(this);
            weight.setGravity(Gravity.LEFT);
            weight.setPadding(10, 10, 10, 10);
            weight.setText(ingretablerow.getQuantity());
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
        listView.setAdapter(stepAdapter);
    }

    private void getBundle() {
        Bundle bundle = this.getIntent().getExtras();
        recipename = bundle.getString("recipename");
        Log.v("recipename:",recipename);
    }

    private void loadImage(){
        String path = Config.SERVER_URL+"/public/images/"+image;
        Log.v("image path:", path);
        if(image!="" && !image.equals("") && image!=null){
            syncImageLoader = new SyncImageLoader();
            syncImageLoader.loadImage(0, path, imageLoadListener);
        }else{
            recipe_img.setVisibility(View.GONE);
        }


    }

    SyncImageLoader.OnImageLoadListener imageLoadListener = new SyncImageLoader.OnImageLoadListener(){

        @Override
        public void onImageLoad(Integer t, Drawable drawable) {
            //BookModel model = (BookModel) getItem(t);
            recipe_img.setBackgroundDrawable(drawable);
        }
        @Override
        public void onError(Integer t) {
        }

    };
}

