package com.android.nurriture.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.nourriture.nourriture.NutritionDetailActivity;
import com.android.nourriture.nourriture.R;
import com.android.nourriture.nourriture.RecipeActivity;
import com.android.nourriture.nourriture.SearchResultActivity;
import com.android.nuttriture.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/12/9.
 */
public class NutritionFragment extends Fragment{

    private String[] nutrition = {"Nutrient","Mineral"};

    private String[] Nutrient = {"Vitamin A","Vitamin B1","Vitamin B2","Vitamin B3","Vitamin B4","Vitamin B5","Vitamin B6",
            "Vitamin C","Vitamin D","Vitamin E","Vitamin H","Vitamin K","Vitamin P","Vitamin B11","Vitamin B12"};

    private Context context;
    private int screenWidth;

    private MyGridView gv_nutrition;
    private MyGridView gv_mineral;
    private List<String> nutritionList;
    private List<String> mineralList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.context = container.getContext();
        View nutritionView = (View)inflater.inflate(R.layout.layout_nutrition, container, false);

        initNutritionGV(nutritionView,inflater);
        initMineralGV(nutritionView,inflater);

       /* DisplayMetrics dm = new DisplayMetrics();
        //取得窗口属性
=======
        DisplayMetrics dm = new DisplayMetrics();

>>>>>>> origin/master
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

        screenWidth = dm.widthPixels;

        ScrollView scrollView = (ScrollView)nutritionView.findViewById(R.id.ingredientScroll);

        LinearLayout linearLayout = (LinearLayout)nutritionView.findViewById(R.id.ingredientTypeView);
        for (int i = 0; i < nutrition.length; i++){
            View deView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.listview_fragement_layout,null);
            TextView title= (TextView)deView.findViewById(R.id.ingredientTypetitle);
            title.setText(nutrition[i]);
            Log.v("for:%d", nutrition[i]);
            TableLayout tableLayout = (TableLayout)deView.findViewById(R.id.ingredientTypeTable);
            TableLayout.LayoutParams tablelayout = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);
            tablelayout.setMargins(10, 10, 10, 10);
            TableRow tablerow = new TableRow(this.getActivity().getApplicationContext());
            tablerow.setLayoutParams(tablelayout);
            tablerow.setGravity(Gravity.CENTER);
            tableLayout.addView(tablerow);

            TableRow.LayoutParams tablerowlayout =
                    new TableRow.LayoutParams((screenWidth-120)/3,TableRow.LayoutParams.WRAP_CONTENT);
            Log.v("the width is :",tablerow.getWidth()+"");
            tablerowlayout.setMargins(10, 10, 10, 10);
            for(int j = 0; j < Nutrient.length; j++){
                final TextView tv = new TextView(this.getActivity().getApplicationContext());
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundResource(R.drawable.rounded_outer3);
                tv.setPadding(15, 15, 10, 10);
                tv.setText(Nutrient[j]);
                tv.setTextColor(android.graphics.Color.parseColor("#666666"));
                tv.setLayoutParams(tablerowlayout);
                tablerow.addView(tv);
                if(((j+1) % 3 == 0)&&(j != Nutrient.length-1)){
                    tablerow = new TableRow(this.getActivity());
                    tableLayout.addView(tablerow);
                    tablerow.setLayoutParams(tablelayout);
                    tablerow.setGravity(Gravity.CENTER);
                }
            }

            deView.setVisibility(View.VISIBLE);
            linearLayout.addView(deView);

        }
        ViewGroup vg = (ViewGroup)nutritionView;*/
        return nutritionView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initNutritionGV(View view,LayoutInflater inflater)
    {
        gv_nutrition = (MyGridView)view.findViewById(R.id.gv_nutrition);
        nutritionList = new ArrayList<String>();
        nutritionList.add("Vitamin A");
        nutritionList.add("Vitamin B1");
        nutritionList.add("Vitamin B2");
        nutritionList.add("Vitamin B3");
        nutritionList.add("Vitamin B4");
        nutritionList.add("Vitamin B5");
        nutritionList.add("Vitamin B6");
        nutritionList.add("Vitamin C");
        nutritionList.add("Vitamin D");
        nutritionList.add("Vitamin E");
        nutritionList.add("Vitamin H");
        nutritionList.add("Vitamin K");
        nutritionList.add("Vitamin P");
        nutritionList.add("Vitamin B11");
        nutritionList.add("Vitamin B12");
        gv_nutrition.setAdapter(new MyGridViewAdapter(nutritionList, inflater));
        gv_nutrition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    }

    private void initMineralGV(View view,LayoutInflater inflater)
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
    }

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
