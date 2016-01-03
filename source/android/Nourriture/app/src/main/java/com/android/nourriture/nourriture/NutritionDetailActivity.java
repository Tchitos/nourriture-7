package com.android.nourriture.nourriture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.nurriture.entity.NutritionInfo;
import com.android.nurriture.util.HttpMethod;
import com.android.nurriture.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/29.
 */
public class NutritionDetailActivity extends Activity {
    private ImageView back_img;

    private TextView nutritionName,nutrition_effect,nutritionRecommand,nutrition_supply;
    private TextView effect,recommend,supplement;

    String effectString = "";
    String recommedndString ="";
    String supplementString ="";

    private NutritionInfo nutritionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_nutrition);

        init();
        initData();
    }

    private void init() {
        back_img = (ImageView) findViewById(R.id.back_img);
        nutritionName = (TextView) findViewById(R.id.nutritionName);
        nutrition_effect = (TextView) findViewById(R.id.nutrition_effect);
        nutritionRecommand = (TextView) findViewById(R.id.nutritionRecommand);
        nutrition_supply = (TextView) findViewById(R.id.nutrition_supply);
        effect = (TextView) findViewById(R.id.effect);
        recommend = (TextView) findViewById(R.id.recommend);
        supplement = (TextView) findViewById(R.id.supplement);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutritionDetailActivity.this, MainActivity.class);
                intent.putExtra("currentIndex", 2);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NutritionDetailActivity.this, MainActivity.class);
        intent.putExtra("currentIndex", 2);
        startActivity(intent);
        finish();
    }

    private void initData()
    {
        Intent intent = getIntent();
        nutritionInfo = (NutritionInfo)intent.getSerializableExtra("nutrition");


        nutritionName.setText(nutritionInfo.getName());
        nutrition_effect.setText("Effect of "+nutritionInfo.getName()+":");
        nutritionRecommand.setText(nutritionInfo.getName() +  " recommended daily intake:");
        nutrition_supply.setText(nutritionInfo.getName() + " supplements cycle:");
        effect.setText(nutritionInfo.getEffect());
        recommend.setText(nutritionInfo.getDailyIntake());
        supplement.setText(nutritionInfo.getSupplementCycle());




        /*Map<String,String> map = new HashMap<String,String>();
        map.put("name",name);
        HttpUtil connectNet = new HttpUtil("/getNutritionByName", HttpMethod.POST, map) {

            @Override
            protected void getResult(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String statusCode = jsonObject.getString("statusCode");
                    if(statusCode == "401"){
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }else if(statusCode == "201"){

                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                    }else{
                        String value = jsonObject.getString("value");
                        Toast.makeText(getApplicationContext(), "value:" + value, Toast.LENGTH_SHORT).show();
                        Log.e("wzzzvaluee:", value);

                        JSONObject nutrition = new JSONObject(value);
                        String dailyIntake = nutrition.getString("dailyIntake");
                        String supplementCycle = nutrition.getString("supplementCycle");
                        //recommedndString = dailyIntake;
                        //Toast.makeText(getApplicationContext(), "a:" + a.get("a"), Toast.LENGTH_SHORT).show();
                        //JSONArray jsonArray = new JSONArray(value);
                       // Log.e("wzzzvalue2:", jsonArray.toString());

                        //String test = "{\"name\":\"Vitamin A\",\"type\":\"nutrient\"}";
                        *//*JSONObject nutrition = new JSONObject(test);  ;
                        Log.e("wzzzvalue:", nutrition.toString());*//*
                        *//*String effect_text = nutrition.getString("effects");
                        String dailyIntake = nutrition.getString("dailyIntake");
                        String supplementCycle = nutrition.getString("supplementCycle");*//*

                        //JSONArray effect_array = new JSONArray(effect_text);


                        effect.setText("shenmaqingkuang!");
                        recommend.setText(dailyIntake);
                        supplement.setText(supplementCycle);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        connectNet.execute();*/
    }
}
