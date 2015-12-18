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


import com.android.nurriture.entity.StepInfo;
import com.android.nuttriture.adapter.StepAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/13.
 */
public class RecipeDetailActivity extends Activity{

    private String[] materials = {"Cabbage","Black fungus","Dry pepper","Salt",
            "Chicken powder","Vinegar","Ginger","Garlic","Peanut oil"};
    private String[] much = {"250g","10g","2","4g",
            "2g","1spoon","3slice","3","5g"};
    private String number[]={"1","2","3","4","5","6"};
    private String desc="Meimei's wings to ok pomelo.";
    private String img;

    private ImageView back_img;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_recipe);
        DisplayMetrics  dm = new DisplayMetrics();
        //ȡ�ô�������
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        //���ڵĿ��
        screenWidth = dm.widthPixels;

        //���ڸ߶�
        screenHeight = dm.heightPixels;
        init();
        initTable();
        initList();
    }

    private void init()
    {
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
        tablerowlayout.setMargins(0,0,0,2);

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
        ListView listView = (ListView)findViewById(R.id.recipe_steps);
        List<StepInfo> stepList = new ArrayList<StepInfo>();
        StepInfo stepInfo;
        for(int i = 0; i < number.length; i++){
            Log.v("number length:",i+"");
            stepInfo = new StepInfo();
            stepInfo.setNumber(number[i]);
            stepInfo.setDesc(desc);
            stepInfo.setImg(img);
            stepList.add(stepInfo);
        }
        StepAdapter stepAdapter = new StepAdapter(stepList,this.getLayoutInflater());
        listView.setAdapter(stepAdapter);

        fixListViewHeight(listView,stepAdapter);
    }

    public void fixListViewHeight(ListView listView,StepAdapter stepAdapter) {
        // ���û��������������������ListViewû��������ء�

        int totalHeight = 0;
        for (int index = 0, len = stepAdapter.getCount(); index < len; index++) {
            View listViewItem = stepAdapter.getView(index , null, listView);
            // ��������View �Ŀ��
            listViewItem.measure(0, 0);
            // ������������ĸ߶Ⱥ�
            totalHeight += listViewItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // listView.getDividerHeight()��ȡ�����ָ����ĸ߶�
        // params.height����ListView��ȫ��ʾ��Ҫ�ĸ߶�
        params.height = totalHeight+ (listView.getDividerHeight() * (stepAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}

